import java.util.*;
import java.io.*;

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	private static long getCount(long m, ArrayList<Long> sizes) {
		long[] count = new long[32];

		long currM = m;
		for(int i = sizes.size() - 1; i >= 0; i--) {
			long curr = 1 << (2 * sizes.get(i));
			if(currM >= curr) currM -= curr;
			else {
				currM = m - curr;
				count++;
			}
		}

		return count;
		
	}

	private static long getCount(long m, ArrayList<Long> sizes) {
		long count = 1;

		long currM = m;
		for(int i = sizes.size() - 1; i >= 0; i--) {
			long curr = 1 << (2 * sizes.get(i));
			if(currM >= curr) currM -= curr;
			else {
				currM = m - curr;
				count++;
			}
		}

		return count;
		
	}

	public static void output(long m, ArrayList<Long> sizes) throws Exception {
		Collections.sort(sizes);
		out.print(getCount(m * m, sizes));
	}

	public static void main(String args[]) throws Exception {		
		int testCases = Integer.parseInt(in.readLine());
		for(int i = 0; i < testCases; i++) {
			StringTokenizer tokenizer = new StringTokenizer(in.readLine());
			long n = Long.parseLong(tokenizer.nextToken()),
				 m = Long.parseLong(tokenizer.nextToken());
			
			ArrayList<Long> sizes = new ArrayList<Long>();
			for(int j = 0; j < n; j++)
				sizes.add(Long.parseLong(tokenizer.nextToken()));

			out.print("Case #" + (i + 1) + ": ");
			output(m, sizes);
			out.println();
		}

		out.flush();
	}
}
