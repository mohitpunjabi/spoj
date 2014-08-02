import java.util.*;
import java.io.*;

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	private static boolean hasOil(long x, long y) {
		if(x >= 0 && y >= 1 - x && y <= 1 + x)	return (x % 2 == 1);
		else if(x < 0 && y >= x && y <= -x)		return (x % 2 == 0);
		else if(y % 2 == 0) 					return true;

		return false;
	}

	public static void output(long x, long y) throws Exception {
		out.print(hasOil(x, y)? "YES": "NO");
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
