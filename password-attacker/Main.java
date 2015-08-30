import java.util.*;
import java.io.*;

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	private static int LIMIT = 101;
	private static final long MOD = (long) 1e9 + 7;
	private static long[][] ways;
	private static long[][] c;

	static {
		c = new long[LIMIT][LIMIT];
		ways = new long[LIMIT][LIMIT];
		for(int i = 0; i < LIMIT; i++) ways[1][i] = 1;
		for(int i = 0; i < LIMIT; i++) c[i][0] = 1;

		for(int m = 1; m < LIMIT; m++) 
			for(int n = 1; n <= m; n++) 
				c[m][n] = (c[m - 1][n] + c[m - 1][n - 1]) % MOD;

		for(int m = 1; m < LIMIT; m++) {
			for(int n = 0; n < LIMIT; n++) {
				if(n < m) {
					ways[m][n] = 0;
					continue;
				}
				for(int i = n - 1; i >= m - 1; i--) {
					ways[m][n] += (c[n][n - i] * ways[m - 1][i]) % MOD;
					ways[m][n] %= MOD;
				}
			}
		}
	}

	public static void output(int m, int n) throws Exception {
		out.print(ways[m][n]);
	}

	public static void main(String args[]) throws Exception {		
		int testCases = Integer.parseInt(in.readLine());
		for(int i = 0; i < testCases; i++) {
			StringTokenizer tokenizer = new StringTokenizer(in.readLine());
			int m = Integer.parseInt(tokenizer.nextToken()),
				n = Integer.parseInt(tokenizer.nextToken());

			out.print("Case #" + (i + 1) + ": ");
			output(m, n);
			out.println();
		}

		out.flush();
	}
}
