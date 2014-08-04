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
	private boolean[] cache;
	private int size;
	private int capacity;
	private int cacheMisses;
	private ArrayList<Integer> requests;
	private int requestsProcessed;

	public Cache(int capacity, ArrayList<Integer> requests) {
		this.capacity = capacity;
		this.requests = requests;
		requestsProcessed = 0;
		cache = new boolean[Cache.LIMIT];
		cacheMisses = 0;

		for(int i = 0; i < cache.length; i++) cache[i] = false;
		size = 0;
	}

	public void request(int item) {
		if(!contains(item)) {
			cacheMisses++;
			add(item);
		}

		requestsProcessed++;
	}

	public int getCacheMisses() {
		return cacheMisses;
	}

	private void add(int item) {
		if(size == capacity) remove(findRemovableItem());
		cache[item] = true;
		size++;
	}

	public void remove(int item) {
		cache[item] = false;
		size--;
	}

	public boolean contains(int item) {
		return cache[item];
	}

	private int findRemovableItem() {
		boolean[] shouldRemove = new boolean[Cache.LIMIT];

		for(int i = 0; i < shouldRemove.length; i++) shouldRemove[i] = true;

		int removableItems = size;
		for(int i = requestsProcessed; removableItems > 1 && i < requests.size(); i++) {
			int currItem = requests.get(i);
			if(shouldRemove[currItem] && contains(currItem)) {
				shouldRemove[currItem] = false;
				removableItems--;
			}
		}

		for(int i = 0; i < shouldRemove.length; i++) if(contains(i) && shouldRemove[i]) return i;
		return 0;
	}

}
