import java.util.*;
import java.io.*;

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	private static final int N_LIMIT = 101;
	private static double[][] mahonian;
	
	private static final double MULTIPLIER = 1000;

	static {
		mahonian = new double[N_LIMIT][N_LIMIT * N_LIMIT];
		mahonian[1][0] = MULTIPLIER;
		for(int i = 2; i < N_LIMIT; i++) {
			int max = (i * (i - 1)) / 2;
			for(int j = 0; j <= max; mahonian[i][j++] = 0.0);

			for(int j = 0; j < i; j++) {
				int maxPrev = ((i - 1) * (i - 2)) / 2;
				for(int k = 0; k <= maxPrev; k++)
					mahonian[i][j + k] += mahonian[i - 1][k];
			}

			for(int j = 0; j <= max; mahonian[i][j++] /= i);
		}
	}

	public static void printArr(int[] a) {
		for(int p = 0; p < a.length; p++)
			System.err.print(a[p] + " ");
		System.err.println();
	}

	public static int getInversions(int[] a) {
		int inversions = 0;
		for(int i = a.length - 1; i >= 0; i--) {
			for(int j = 0; j < i; j++) {
				if(a[j + 1] < a[j]) {
					int temp = a[j];
					a[j] = a[j + 1];
					a[j + 1] = temp;
					inversions++;

				}
			}
		}

		return inversions;
	}

	public static double expectedWithoutShuffle(int[] a, int n, long k) {
		double inversions = (double) getInversions(a);
		if(inversions < k) return 0;
		return inversions - k;
	}


	public static double expectedWithShuffle(int[] a, int n, long k) {
		if(k == 0) return getInversions(a);
		int maxSwaps = (n * (n - 1)) / 2;
		if(k >= maxSwaps) return 0;
		k--;
		double expected = 0;
		for(long i = k + 1; i <= maxSwaps; i++)
			expected += mahonian[n][(int) i] * (i - k);
		return expected / MULTIPLIER;
	}

	public static void output(int[] a, int n, long k) throws Exception {
		double 	without = expectedWithoutShuffle(a, n, k),
				with = expectedWithShuffle(a, n, k);

		out.printf("%.6f", Math.min(with, without));
	}

	public static void main(String args[]) throws Exception {
		int testCases = Integer.parseInt(in.readLine());
		for(int i = 0; i < testCases; i++) {
			StringTokenizer tokenizer = new StringTokenizer(in.readLine());
			int n = Integer.parseInt(tokenizer.nextToken());
			long k = Long.parseLong(tokenizer.nextToken());

			int[] a = new int[n];
			tokenizer = new StringTokenizer(in.readLine());
			for(int j = 0; j < n; j++) 
				a[j] = Integer.parseInt(tokenizer.nextToken());

			output(a, n, k);
			out.println();
		}

		out.flush();
	}
}
