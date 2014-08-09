import java.util.*;
import java.io.*;

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	private static int H_LIMIT = (int) (1e7) + 4;
	private static int H_PAD = (int) (4 * (1e6)) + 1;
	private static int N_LIMIT = (int) (1e6) + 2;

	private static long[] sum;
	private static int[] last;

	private static int getLast(long height) {
		return last[(int) height + H_PAD];
	}

	private static void setLast(long height, int val) {
		last[(int) height + H_PAD] = val;
	}

	public static void output(long[] heights) throws Exception {
		int n = heights.length;
		for(int i = n - 1; i > 0; i--) heights[i] -= heights[i - 1];
		for(int i = 0; i <= n; sum[i++] = 0);
		n--;

		sum[0] = 1;
		for(int i = 1; i <= n; i++) {
			sum[i] = ModMath.add(sum[i - 1], sum[i - 1]);
			int lastI = getLast(heights[i]);
			if(lastI > 0) sum[i] = ModMath.add(sum[i], -sum[lastI - 1]);
			setLast(heights[i], i);
		}

		out.print(ModMath.add(sum[n], (long) -1));
		for(int i = 1; i <= n; setLast(heights[i++], 0));
	}

	public static void main(String args[]) throws Exception {		
		int testCases = Integer.parseInt(in.readLine());
		sum = new long[N_LIMIT];
		last = new int[H_LIMIT];
		for(int i = 0; i < H_LIMIT; last[i++] = 0);
		for(int i = 0; i < testCases; i++) {
			int n = Integer.parseInt(in.readLine());
			long[] heights = new long[n];
			StringTokenizer tokenizer = new StringTokenizer(in.readLine());
			for(int j = 0; j < n; j++) heights[j] = (long) Integer.parseInt(tokenizer.nextToken());

			output(heights);
			out.println();
		}

		out.flush();
	}
}

class ModMath {
	private static final long MOD = ((long) 1e9) + 9;

	public static long add(Long ... nums) {
		long sum = 0;
		for(long num : nums) {
			sum += num;
			if(sum > MOD) 		sum %= MOD;
			else if(sum < 0)	sum += MOD;
		}

		return sum;
	}

	public static long mul(Long ... nums) {
		long product = 1;
		for(long num : nums) product = (product * (num % MOD)) % MOD;
		return product;
	}
}
