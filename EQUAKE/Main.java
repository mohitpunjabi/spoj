import java.util.*;
import java.io.*;

class Building {

	private ArrayList<Integer> heights;
	private int rotation;
	private int length;

	public int minHeight;
	public int maxHeight;

	private static ArrayList<ArrayList<Integer>> heightsLookup;
	private static final int HEIGHT_LIMIT = 10000;
	private static int[][] lcmLookup; 

	public static Building ZERO;


	static {
		lcmLookup = new int[70][70];
		for(int i = 0; i < 70; i++)
			for(int j = 0; j < 70; j++) lcmLookup[i][j] = 0;

		heightsLookup = new ArrayList<ArrayList<Integer>>(HEIGHT_LIMIT);
		for(int i = 0; i < HEIGHT_LIMIT; i++)
			heightsLookup.add(new ArrayList<Integer>());

		ZERO = new Building("0");
	}

	public Building(String initHeight) {
		length = initHeight.length();
		rotation = 0;
		heights = heightsLookup.get(Integer.parseInt(initHeight));
		if(heights.size() == 0)
			populateHeights(heights, initHeight);

		minHeight = Collections.min(heights);
		maxHeight = Collections.max(heights);
	}

	public Building(ArrayList<Integer> heights) {
		this.heights = heights;
		rotation = 0;
		length = heights.size();

		minHeight = Collections.min(heights);
		maxHeight = Collections.max(heights);
	}

	public Building(Building b) {
		copyOf(b);
	}

	public void destroy(int force) {
		rotation = (rotation + force) % length;
	}

	public int getHeight() {
		return heights.get(rotation);
	}

	public ArrayList<Integer> allHeights() {
		return heights;
	}

	public int getLength() {
		return length;
	}
	
	public int getRotation() {
		return rotation;
	}

	public String toString() {
		String string = "(" + getHeight() + "): ";
		for(int height : heights) string += height + " ";
		return string; 
	}

	public static Building max(Building a, Building b) {
		if(a == null)					  return b;
		if(b == null)					  return a;
		if(a.getHeight() > b.getHeight()) return a;
		return b;
	}

	public void copyOf(Building b) {
		heights = new ArrayList<Integer>(b.allHeights());
		minHeight = b.minHeight;
		maxHeight = b.maxHeight;
		length = b.getLength();
		rotation = b.getRotation();
	}

	public void combine(Building a, Building b) {
		int mergedLength = lcm(a.getLength(), b.getLength());
		ArrayList<Integer> aHeights = a.allHeights(),
			 			   bHeights = b.allHeights();

		heights = new ArrayList<Integer>(mergedLength);
		for(int i = 0; i < mergedLength; i++) {
			int aHeight = aHeights.get((a.getRotation() + i) % a.getLength()),
				bHeight = bHeights.get((b.getRotation() + i) % b.getLength());

			int maxHeight = (aHeight > bHeight)? aHeight: bHeight;
			heights.add(maxHeight);
		}
		rotation = 0;
	}

	public static Building newMergedBuilding(Building a, Building b) {
		ArrayList<Integer> aHeights = a.allHeights(),
			 			   bHeights = b.allHeights();

		if(a.minHeight >= b.maxHeight)		return new Building(a);
		else if(b.minHeight >= a.maxHeight)	return new Building(b);

		int mergedLength = lcm(a.getLength(), b.getLength());
		ArrayList<Integer> mergedHeights = new ArrayList<Integer>(mergedLength);
		
		for(int i = 0; i < mergedLength; i++) {
			int aHeight = aHeights.get(i % a.getLength()),
				bHeight = bHeights.get(i % b.getLength());

			int maxHeight = (aHeight > bHeight)? aHeight: bHeight;
			mergedHeights.add(maxHeight);
		}

		return new Building(mergedHeights);
	}

	private static int lcm(int a, int b) {
		if(lcmLookup[a][b] == 0) lcmLookup[a][b] = a * b / gcd(a, b);
		return lcmLookup[a][b];
	}

	private static int gcd(int a, int b) {
		if(b == 0) return a;
		return gcd(b, a % b);
	}

	private static void populateHeights(ArrayList<Integer> heights, String initHeight) {
		String currHeight = initHeight;
		int length = currHeight.length();
		char[] h = currHeight.toCharArray();
		for(int i = 0; i < length; i++) {
			heights.add(Integer.parseInt(currHeight));

			char first = h[0];
			for(int j = 0; j < h.length - 1; j++) h[j] = h[j + 1];
			h[h.length - 1] = first;

			currHeight = new String(h);
		}
	}

}


class SegmentTree {

	class Node {
		private Building value;
		public int from;
		public int to;
		public Node left;
		public Node right;

		public int lazy;

		public Node(Building value, int from, int to, Node left, Node right) {
			this.value = value;
			this.from = from;
			this.to = to;
			this.left = left;
			this.right = right;
			this.lazy = 0;
		}

		public Node(Building value, int from, int to) {
			this(value, from, to, null, null);
		}

		public void update(int force) {
			value.destroy(force);			
		}

		public boolean completelyIn(int from, int to) {
			return this.from >= from && this.to <= to;
		}

		public boolean completelyOut(int from, int to) {
			return this.from > to || this.to < from;
		}

		public boolean isLeaf() {
			return this.left == null && this.right == null;
		}

		public void lazyUpdate() {
			if(lazy != 0) {
				update(lazy);
				if(!isLeaf()) {
					left.lazy += lazy;
					right.lazy += lazy;
				}

				lazy = 0;
			}
		}

		public String toString() {
			return value.toString() + " [" + from + ", " + to + "]";
		}
	}	

	private Node root;

	public SegmentTree(ArrayList<Building> elems) {
//		root = build(elems, 0, elems.size() - 1);
		root = buildTopDown(elems);
	}

	public Building getMaxIn(int from, int to) {
			return _getMaxIn(root, from, to);
	}

	public void update(int from, int to, int force) {
		_update(root, from, to, force);
	}

	private void _update(Node node, int from, int to, int force) {
		if(node == null) return;
		
		node.lazyUpdate();
		if(node.completelyOut(from, to)) return;
		if(node.completelyIn(from, to)) {
			node.update(force);
			if(!node.isLeaf()) {
				node.left.lazy += force;
				node.right.lazy += force;
			}
			return;
		}

		_update(node.left, from, to, force);
		_update(node.right, from, to, force);

		node.value.combine(node.left.value, node.right.value);
	}

	private Building _getMaxIn(Node node, int from, int to) {
		if(node.completelyOut(from, to))	return null;
		node.lazyUpdate();
		if(node.completelyIn(from, to))		return node.value;

		return Building.max(_getMaxIn(node.left, from, to), _getMaxIn(node.right, from, to));
	}

	private int pow2Round(int n) {
		n--;
		n |= n >> 1; 
		n |= n >> 2;
		n |= n >> 4;
		n |= n >> 8;
		n |= n >> 16;
		return n + 1;  
	}

	private Node buildTopDown(ArrayList<Building> elems) {
		int numElems = elems.size();

		int newNumElems = pow2Round(numElems);
		Node[] nodes = new Node[2 * newNumElems + 1];
		for(int i = 0; i < newNumElems; i++) {
			if(i < numElems)	nodes[newNumElems + i] = new Node(elems.get(i), i, i);
			else				nodes[newNumElems + i] = new Node(Building.ZERO, i, i);
		}

		numElems = newNumElems;

		for(int level = numElems; level > 1; level /= 2) {
			for(int i = level; i < 2 * level - 1; i += 2) {
		  		Building merged = Building.newMergedBuilding(nodes[i].value, nodes[i + 1].value);
				nodes[i / 2] = new Node(merged, nodes[i].from, nodes[i + 1].to, nodes[i], nodes[i + 1]);
			}
		}
		
		return nodes[1];
	}
	private Node build(ArrayList<Building> elems, int from, int to) {
		if(from == to) return new Node(elems.get(from), from, to);

		int mid = (from + to) / 2;
		Node left = build(elems, from, mid);
		Node right = build(elems, mid + 1, to);

		return new Node(Building.newMergedBuilding(left.value, right.value), from, to, left, right);
	}

	public void print() {
		_print(root);
	}

	private void _print(Node node) {
		if(node != null) {
			_print(node.left);
			System.out.println(node.toString());
			_print(node.right);
		}
	}

}

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public static void output(SegmentTree tree, int type, int l, int r) throws Exception {
		out.print(tree.getMaxIn(l, r).getHeight());
	}

	public static void main(String args[]) throws Exception {		
		int n = Integer.parseInt(in.readLine());
		ArrayList<Building> elems = new ArrayList<Building>(n);
		StringTokenizer tokenizer = new StringTokenizer(in.readLine());
		for(int i = 0; i < n; i++) {
			Building building = new Building(tokenizer.nextToken());
			elems.add(building);
//			System.out.println("Added building: " + building.toString());
		}

		SegmentTree tree = new SegmentTree(elems);

//		for(int i = 0; i < n ; i++) System.out.print(elems.get(i).toString() + " ");
//		System.out.println();
//		tree.print();

		int m = Integer.parseInt(in.readLine());

		for(int i = 0; i < m; i++) {
			tokenizer = new StringTokenizer(in.readLine());
			String type = tokenizer.nextToken();
			if(type.charAt(0) == '0') {
				int l = Integer.parseInt(tokenizer.nextToken()),
					r = Integer.parseInt(tokenizer.nextToken()),
					f = Integer.parseInt(tokenizer.nextToken());

				tree.update(l, r, f);
			}
			else {
				int l = Integer.parseInt(tokenizer.nextToken()),
					r = Integer.parseInt(tokenizer.nextToken());

				output(tree, 1, l, r);
				out.println();
			}

		}

		out.flush();
	}
}
