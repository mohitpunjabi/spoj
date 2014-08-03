import java.util.*;
import java.io.*;

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public static void output(Graph graph, int n) throws Exception {
		int distance = graph.getDistance(1, n);
		out.print((distance > n)? -1: distance);
//		out.print(graph.toString());
	}

	public static void main(String args[]) throws Exception {		
		StringTokenizer tokenizer = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(tokenizer.nextToken()),
			m = Integer.parseInt(tokenizer.nextToken());

		Graph graph = new Graph(n, m);
		for(long i = 0; i < m; i++) {
			tokenizer = new StringTokenizer(in.readLine());
			int from = Integer.parseInt(tokenizer.nextToken()),
				to = Integer.parseInt(tokenizer.nextToken());

			graph.connect(from, to);
//			out.println("Connecting " + from + ", " + to);
//			output(graph, n);
//			out.flush();
		}

		output(graph, n);
		out.println();
		out.flush();
	}
}

class Graph {

	protected Map<Integer, Vertex> vertices;
	protected Map<String, Edge> edges;

	public Graph(int vertexCount, int edgeCount) {
		edges = new HashMap<String, Edge>(2 * edgeCount);
		vertices = new HashMap<Integer, Vertex>(vertexCount);
		for(int i = 1; i <= vertexCount; i++) vertices.put(i, new Vertex(i, vertexCount + 1, false));

		vertices.get(1).distance = 0;
	}

	public void connect(int from, int to) {
		if(from == to) return;

		join(from, to, 0);
		join(to, from, 1);
	}

	private void join(int from, int to, int cost) {
		int currentCost = getEdgeCost(from, to);
		
		if(currentCost > vertices.size()) {
			Edge edge = new Edge(from, to, cost);
			vertices.get(from).add(edge);
			edges.put(edgeKey(from, to), edge);
		}
		else if(cost < currentCost)
			edges.get(edgeKey(from, to)).cost = cost;
	}

	private int getEdgeCost(int from, int to) {
		if(from == to) return 0;

		Edge edge = edges.get(edgeKey(from, to));
		return (edge == null)? vertices.size() + 1: edge.cost;
	}

	private String edgeKey(int from, int to) {
		return ((long) from * vertices.size() + to) + "";
	}

	public int getDistance(int from, int to) {
		VertexComparator comparator = new VertexComparator();
		PriorityQueue<Vertex> unvisited = new PriorityQueue<Vertex>(vertices.size(), comparator);

		for(Map.Entry<Integer, Vertex> vertex : vertices.entrySet()) {
			vertex.getValue().distance = vertices.size() + 1;
			vertex.getValue().visited = false;
		}
	
		vertices.get(from).distance = 0;
		for(Map.Entry<Integer, Vertex> vertex : vertices.entrySet())
			unvisited.add(vertex.getValue());

		while(unvisited.size() > 0) {
			Vertex v = unvisited.remove();

//			System.out.println("Popped: " + v.index);
			if(!v.visited) {
				if(v.index == to || v.distance > vertices.size()) return v.distance;
				v.relaxEdges(unvisited);
				v.visited = true;
			}
		}
		return vertices.get(to).distance;
	}

	public String toString() {
		String value = "";
		for(Map.Entry<Integer, Vertex> vertex : vertices.entrySet()) {
			value += (vertex.getKey() + ": ");
			ArrayList<Edge> edges = vertex.getValue().edges;

			for(int i = 0; i < edges.size(); i++) value += (edges.get(i).toString() + " ");
			value += "\n";
		}

		return value;
	}

	class Vertex {
		public ArrayList<Edge> edges;
		public int index;
		public int distance;
		public boolean visited;

		public Vertex(int index, int distance, boolean visited) {
			this.index = index;
			edges = new ArrayList<Edge>(1);
		}

		public void add(Edge edge) {
			edges.add(edge);
		}

		public void relaxEdges(PriorityQueue<Vertex> unvisited) {
			Map<Integer, Vertex> vertices = Graph.this.vertices;
			for(Edge edge : edges) {
				Vertex to = vertices.get(edge.to);
				int newDistance = distance + edge.cost;
				if(newDistance < to.distance) {
//					unvisited.remove(to);
					to.distance = newDistance;
					unvisited.add(to);
				}
			}
/*
			System.out.println("Relaxed edges for " + index);
			for(Map.Entry<Integer, Vertex> vertex : vertices.entrySet())
				System.out.print(vertex.getValue().distance + " ");
			System.out.println();
*/
		}
	}

	class Edge {
		public int from;
		public int to;
		public int cost;

		public Edge(int from, int to, int cost) {
			this.from = from;
			this.to = to;
			this.cost = cost;
		}

		public String toString() {
			return "(" + from + ", " + to + ")[" + cost + "]";
		}
	}

	class VertexComparator implements Comparator<Vertex> {
		
		public int compare(Vertex v1, Vertex v2) {
			if(v1.distance < v2.distance)	return -1;
			if(v1.distance > v2.distance)	return 1;
			
			return 0;
		}
	}

	class VertexPriorityQueue {
		int[] vertices;

		public VertexPriorityQueue(int capacity) {
			vertices = new int[capacity];
		}

		public void add(Vertex v) {
		}

		public void remove(Vertex v) {
		}

		public int compare(int v1, int v2) {
			return 0;
		}
	}
}
