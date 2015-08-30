import java.util.*;
import java.io.*;

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public static void output(int n, int m, int max, int sum) throws Exception {
		int needed = m - (n * max) + sum;
		
		if(needed < 0)				out.print("No");
		else if(needed % n == 0)	out.print("Yes");
		else						out.print("No");
	}

	public static void main(String args[]) throws Exception {		
		int testCases = Integer.parseInt(in.readLine());
		for(int i = 0; i < testCases; i++) {
			StringTokenizer tokenizer = new StringTokenizer(in.readLine());
			int n = Integer.parseInt(tokenizer.nextToken()),
				m = Integer.parseInt(tokenizer.nextToken());

			int sum = 0,
				max = 0;

			tokenizer = new StringTokenizer(in.readLine());
			for(int j = 0; j < n; j++) {
				int a = Integer.parseInt(tokenizer.nextToken());
				sum += a;
				if(a > max) max = a;
			}

			output(n, m, max, sum);
			out.println();
		}

		out.flush();
	}
}
