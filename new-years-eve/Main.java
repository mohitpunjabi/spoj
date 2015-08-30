import java.util.*;
import java.io.*;

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));


	public static void output(int b, int l, int n) throws Exception {
		
	}

	public static void main(String args[]) throws Exception {		
		int testCases = Integer.parseInt(in.readLine());
		for(int i = 0; i < testCases; i++) {

			StringTokenizer tokenizer = new StringTokenizer(in.readLine());
			int b = Integer.parseInt(tokenizer.nextToken()),
				l = Integer.parseInt(tokenizer.nextToken()),
				n = Integer.parseInt(tokenizer.nextToken());

			out.print("Case #" + (i + 1) + ": ");
			output(b, l, n);
			out.println();
		}

		out.flush();
	}
}
