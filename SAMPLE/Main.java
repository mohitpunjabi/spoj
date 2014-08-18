import java.util.*;
import java.io.*;

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public static void output(int x, int y) throws Exception {
	}

	public static void main(String args[]) throws Exception {		
		int testCases = Integer.parseInt(in.readLine());
		for(int i = 0; i < testCases; i++) {
			int n = Integer.parseInt(in.readLine());

			StringTokenizer tokenizer = new StringTokenizer(in.readLine());
			int x = Integer.parseInt(tokenizer.nextToken()),
				y = Integer.parseInt(tokenizer.nextToken());

			out.print("Case #" + (i + 1) + ": ");
			output(x, y);
			out.println();
		}

		out.flush();
	}
}
