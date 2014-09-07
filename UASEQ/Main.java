import java.util.*;
import java.io.*;

class Progression {

	private int startIndex;
	private ArrayList<Long> elems;

	public Progression(int startIndex, ArrayList<Long> elems) {
		this.startIndex = startIndex;
		this.elems = elems;
	}

	public long diff() {
		return elems.get(startIndex + 1) - elems.get(startIndex);
	}

	public long get(int i) {
		return elems.get(startIndex) + (i - startIndex) * diff();
	}

	public String toString() {
		StringBuilder str = new StringBuilder();
//		str.append(" [" + get(startIndex) + " " + get(startIndex + 1) + "]");
		for(int i = 0; i < elems.size(); i++)
			str.append(get(i) + " ");
		return str.toString();
	}

	public int getChangeCount(int limit) {
		int changeCount = 0;
		for(int i = 0; i < elems.size(); i++) {
			if(get(i) != elems.get(i)) changeCount++;
			if(changeCount > limit) return Integer.MAX_VALUE;
		}
		return changeCount;
	}

	public boolean equals(Object obj) {
		if(!(obj instanceof Progression)) return false;
		Progression rhs = (Progression) obj;
		return get(0) == rhs.get(0) && diff() == rhs.diff();
	}

	public int hashCode() {
		return (int) ((get(0) + diff()) % 1000000007);
	}
}

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	static class ProgressionBeautyComparator implements Comparator<Progression> {
	
		public int compare(Progression a, Progression b) {
			if(a.get(0) < b.get(0))			return -1;
			else if(a.get(0) > b.get(0))	return 1;
			else if(a.diff() < b.diff())	return -1;
			else if(a.diff() > b.diff())	return 1;

			return 0;
		}

	}

	public static void output(ArrayList<Long> elems, int k) throws Exception {
		HashMap<Progression, Integer> counts = new HashMap<Progression, Integer>();
		ArrayList<Progression> progressions = new ArrayList<Progression>();
		for(int i = 0; i < elems.size() - 1; i++)
			progressions.add(new Progression(i, elems));
		Collections.sort(progressions, new ProgressionBeautyComparator());

		for(Progression p : progressions) {
			int count = 0;
			if(counts.containsKey(p))	count = counts.get(p);
			else						count = p.getChangeCount(k);
//			System.err.print(p);
//			System.err.println(" " + p.getChangeCount(k));

			if(count <= k) {
				out.print(p);
				return;
			}

			counts.put(p, count);
		}
	}

	public static void main(String args[]) throws Exception {		
		StringTokenizer tokenizer = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(tokenizer.nextToken()),
			k = Integer.parseInt(tokenizer.nextToken());

		ArrayList<Long> elems = new ArrayList<Long>();
		tokenizer = new StringTokenizer(in.readLine());
		for(int i = 0; i < n; i++)
			elems.add(Long.parseLong(tokenizer.nextToken()));

		output(elems, k);
		out.println();
		out.flush();
	}
}
