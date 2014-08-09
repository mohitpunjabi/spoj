import java.util.*;
import java.io.*;

public class ScoreGenerator {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public static long f(ArrayList<Long> a) {
		long s = 0;
		for(int i = 0; i < a.size(); i++) {
			if(i + i < a.size()) s += a.get(i);
			else				 s -= a.get(i);
		}

		return Math.abs(s);
	}

	private static void printArr(ArrayList<Long> a) {
		for(long ai : a) out.print(ai + " ");
		out.println();
	}

	private static void rotate(ArrayList<Long> a, int from, int to) {
		long end = a.get(from);
		for(int i = from; i < to; i++) a.set(i, a.get(i + 1));
		a.set(to, end);
	}

	public static void score(ArrayList<Long> a, ArrayList<Integer> from, ArrayList<Integer> to) throws Exception {
		long init = f(a);

		int rangeSum = 0;
		for(int i = 0; i < from.size(); i++) {
			rotate(a, from.get(i), to.get(i));
			rangeSum += to.get(i) - from.get(i) + 1;

			if(rangeSum > 2 * a.size()) 
				throw new Exception("Range exceeded");
		}

		long s = f(a);
		out.print((double) (init - s) / init);
	}


	public static void main(String args[]) {		
		try {
			int n = Integer.parseInt(in.readLine());
			StringTokenizer tokenizer = new StringTokenizer(in.readLine());
			ArrayList<Long> a = new ArrayList<Long>();
			for(int i = 0; i < n; i++) 
				a.add(Long.parseLong(tokenizer.nextToken()));

			int q = Integer.parseInt(in.readLine());
			ArrayList<Integer> from = new ArrayList<Integer>();
			ArrayList<Integer> to = new ArrayList<Integer>();
			for(int i = 0; i < q; i++) {
				tokenizer = new StringTokenizer(in.readLine());
				from.add(Integer.parseInt(tokenizer.nextToken()) - 1);
				to.add(Integer.parseInt(tokenizer.nextToken()) - 1);
			}

			score(a, from, to);
			out.println();
			out.flush();
		}
		catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
