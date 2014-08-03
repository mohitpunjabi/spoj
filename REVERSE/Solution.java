import java.util.*;
import java.io.*;

class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public static void output(Graph graph, int n) throws Exception {
		int distance = graph.getDistance(1, n);
		out.print((distance > n)? -1: distance);
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
		}

		output(graph, n);
		out.flush();
	}
}

class Graph {

	private Map<Integer, ArrayList<Edge>> vertices;
	protected Map<String, Edge> edges;

	public Graph(int vertexCount, int edgeCount) {
		edges = new HashMap<String, Edge>(edgeCount);
		vertices = new HashMap<Integer, ArrayList<Edge>>(vertexCount);
		for(int i = 1; i <= vertexCount; i++) vertices.put(i, new ArrayList<Edge>(1));
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

		String edgeKey = edgeKey(from, to);
		if(!edges.containsKey(edgeKey))	return vertices.size() + 1;

		return edges.get(edgeKey).cost;
	}

	private String edgeKey(int from, int to) {
		return ((long) from * vertices.size() + to) + "";
	}

	public int getDistance(int from, int to) {
		boolean[] computed = new boolean[vertices.size() + 1];
		int[] distances = new int[vertices.size() + 1];
		LinkedList<Integer> frontier = new LinkedList<Integer>();

		for(int i = 1; i <= vertices.size(); i++) {
			distances[i] = vertices.size() + 1;
			computed[i] = false;
		}
		distances[from] = 0;
		frontier.add(from);

		while(frontier.size() > 0) {
//			System.out.print("Frontier: "); for(int i = 0; i < frontier.size(); i++) System.out.print(frontier.get(i) + " ");
//			System.out.println();

			int vertex = frontier.remove();
			ArrayList<Edge> edges = vertices.get(vertex);
			computed[vertex] = true;

			for(int i = 0; i < edges.size(); i++) {
				Edge edge = edges.get(i);
				int	newDistance = distances[edge.from] + edge.cost;
				if(newDistance < distances[edge.to]) distances[edge.to] = newDistance;
				if(!computed[edge.to]) frontier.add(edge.to);
			}
			
//			for(int i = 1; i < distances.length; i++) System.out.print((distances[i] > vertices.size())? "* ": distances[i] + " ");
//			System.out.println();
		}

		return distances[to];
	}

	public String toString() {
		String value = "";
		for(Map.Entry<Integer, ArrayList<Edge>> vertex : vertices.entrySet()) {
			value += (vertex.getKey() + ": ");
			ArrayList<Edge> edges = vertex.getValue();

			for(int i = 0; i < edges.size(); i++) value += (edges.get(i).toString() + " ");
			value += "\n";
		}

		return value;
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
}
