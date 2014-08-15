import java.util.*;
import java.io.*;

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));


	static class LongComparator implements Comparator<Long> {
	
		public int compare(Long l1, Long l2) {
			return -l1.compareTo(l2);
		}

	}

	private static long getMinimumScalarProduct(ArrayList<Long> x, ArrayList<Long> y) {
		long product = 0;

		LongComparator comparator = new LongComparator();
		Collections.sort(x, comparator);
		Collections.sort(y);
		for(int i = 0; i < x.size(); i++) 
			product += x.get(i) * y.get(i);

		return product;
	}

	public static void output(ArrayList<Long> x, ArrayList<Long> y) throws Exception {
		out.print(getMinimumScalarProduct(x, y));
	}

	public static void main(String args[]) throws Exception {		
		int testCases = Integer.parseInt(in.readLine());
		for(int i = 0; i < testCases; i++) {
			int n = Integer.parseInt(in.readLine());

			ArrayList<Long> x = new ArrayList<Long>(n),
							y = new ArrayList<Long>(n);

			StringTokenizer tokenizer = new StringTokenizer(in.readLine());
			for(int j = 0; j < n; j++) 
				x.add(Long.parseLong(tokenizer.nextToken()));

			tokenizer = new StringTokenizer(in.readLine());
			for(int j = 0; j < n; j++) 
				y.add(Long.parseLong(tokenizer.nextToken()));

			out.print("Case #" + (i + 1) + ": ");
			output(x, y);
			out.println();
		}

		out.flush();
	}
}
