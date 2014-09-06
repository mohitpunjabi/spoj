import java.util.*;
import java.io.*;

class Progression {

	private int startIndex;
	private int start;
	private int diff;
	private int size;
	private ArrayList<Integer> elems;

	public Progression(int startIndex, ArrayList<Integer> elems) {
		this.startIndex = startIndex;
		this.elems = elems;
		this.start = elems.get(startIndex);
		this.diff = (startIndex >= elems.size() - 1)? 0: elems.get(startIndex + 1) - elems.get(start);
		this.size = findSize();
	}

	private int findSize() {
		if(startIndex >= elems.size() - 1) return 1;
		int size = 2;
		for(int i = startIndex + 2; i < elems.size(); i++) {
			if(elems.get(i) - elems.get(i - 1) != diff) break;
			size++;
		}

		return size;
	}

	public int get(int i) {
		return start + (i - startIndex) * diff;
	}

	public int size() {
		return size;
	}

	public String toString() {
		StringBuilder str = new StringBuilder();

		str.append("[" + start + " " + diff + " " + size + "]: ");

		int curr = start;
		for(int i = 0; i < size; i++) {
			str.append(curr).append(" ");
			curr += diff;
		}
		return str.toString();
	}

}

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public static ArrayList<Progression> getProgressions(ArrayList<Integer> elems) {
		ArrayList<Progression> progressions = new ArrayList<Progression>();
		for(int i = 0; i < elems.size(); ) {
			Progression p = new Progression(i, elems);
			System.err.println(p.toString());
			i += p.size();
			progressions.add(p);
		}
		return progressions;
	}

	public static void output(ArrayList<Integer> elems, int k) throws Exception {
		ArrayList<Progression> progressions = getProgressions(elems);
	}

	public static void main(String args[]) throws Exception {		
		StringTokenizer tokenizer = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(tokenizer.nextToken()),
			k = Integer.parseInt(tokenizer.nextToken());

		ArrayList<Integer> elems = new ArrayList<Integer>();
		tokenizer = new StringTokenizer(in.readLine());
		for(int i = 0; i < n; i++)
			elems.add(Integer.parseInt(tokenizer.nextToken()));

		output(elems, k);
		out.println();
		out.flush();
	}
}
