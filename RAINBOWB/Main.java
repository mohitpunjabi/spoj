import java.util.*;
import java.io.*;

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public static final long MOD = ((long) 1e9) + 7;

	public static long c6(int n) {
		if(n < 6) return 0;

		long[][] c = new long[n + 1][7];
		for(int i = 0; i <= n; c[i++][0] = 1);

		for(int i = 1; i <= n; i++)
			for(int j = 1; j <= 6 && j <= i; j++)
				c[i][j] = (c[i -1][j] + c[i - 1][j - 1]) % MOD;

		return c[n][6];
	}

	public static void output(int n) throws Exception {
		out.print(c6((n - 1) / 2));
	}

	public static void main(String args[]) throws Exception {		
		int n = Integer.parseInt(in.readLine());
		output(n);
		out.println();
		out.flush();
	}
}
