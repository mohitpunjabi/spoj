import java.util.*;
import java.io.*;

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public static void output(long x, long y) throws Exception {
	}

	public static void main(String args[]) throws Exception {		
		int testCases = Integer.parseInt(in.readLine());
		for(long i = 0; i < testCases; i++) {
			StringTokenizer tokenizer = new StringTokenizer(in.readLine());
			long x = Long.parseLong(tokenizer.nextToken()),
				 y = Long.parseLong(tokenizer.nextToken());

			output(x, y);
			out.println();
		}

		out.flush();
	}
}
