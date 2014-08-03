import java.util.*;
import java.io.*;

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public static void output(int n, ArrayList<Integer> orders) throws Exception {
		Cache cache = new Cache(n, orders);
		for(int order : orders) cache.request(order);
		out.print(cache.getCacheMisses());
	}

	public static void main(String args[]) throws Exception {		
		int testCases = Integer.parseInt(in.readLine());
		for(long i = 0; i < testCases; i++) {
			StringTokenizer tokenizer = new StringTokenizer(in.readLine());
			int n = Integer.parseInt(tokenizer.nextToken()),
				m = Integer.parseInt(tokenizer.nextToken());

			tokenizer = new StringTokenizer(in.readLine());
			ArrayList<Integer> orders = new ArrayList<Integer>(m);
			for(int j = 0; j < m; j++)
				orders.add(Integer.parseInt(tokenizer.nextToken()));

			output(n, orders);
			out.println();
		}

		out.flush();
	}
}

class Cache {
	public static final int LIMIT = 401;
	private HashSet<Integer> cache;
	private int capacity;
	private int cacheMisses;
	private ArrayList<Integer> requests;
	private int requestsProcessed;

	public Cache(int capacity, ArrayList<Integer> requests) {
		this.capacity = capacity;
		this.requests = requests;
		requestsProcessed = 0;
		cache = new HashSet<Integer>(capacity);
		cacheMisses = 0;
	}

	public void request(int item) {
		if(!cache.contains(item)) {
			cacheMisses++;
			add(item);
		}

		requestsProcessed++;
	}

	public int getCacheMisses() {
		return cacheMisses;
	}

	public String toString() {
		String string = "";
		for(int item : cache) string += item + " ";
		return string;
	}

	private void add(int item) {
		if(cache.size() == capacity) cache.remove(findRemovableItem());
		cache.add(item);
	}

	private int findRemovableItem() {
 		int unusedItem = 0,
			unusedDuration = 0;

		for(int item : cache) {
			int currDuration = getNextRequestTime(item);
			if(currDuration > unusedDuration) {
				unusedItem = item;
				unusedDuration = currDuration;
			}
		}

		return unusedItem;
	}

	private int getNextRequestTime(int item) {
		for(int i = requestsProcessed; i < requests.size(); i++)
			if(requests.get(i) == item) return i;

		return requests.size();
	}
}
