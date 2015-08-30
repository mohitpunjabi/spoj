import java.util.*;
import java.io.*;

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public static void output(int moves, int r, int g, int b) throws Exception {
		while(moves > 0) {
			int max = Math.max(Math.max(r, g), b);
			if(max == r)		r /= 2;
			else if(max == g)	g /= 2;
			else				b /= 2;

			moves--;
		}

		int max = Math.max(Math.max(r, g), b);
		out.print(max);
	}

	private static int getMaxFromList(String list) {
		int max = 0;
		StringTokenizer tokenizer = new StringTokenizer(list);
		while(tokenizer.hasMoreTokens()) {
			int curr = Integer.parseInt(tokenizer.nextToken());
			if(curr > max) max = curr;
		}
		return max;
	}

	public static void main(String args[]) throws Exception {		
		int testCases = Integer.parseInt(in.readLine());
		for(int i = 0; i < testCases; i++) {
			StringTokenizer tokenizer = new StringTokenizer(in.readLine());
			int r = Integer.parseInt(tokenizer.nextToken()),
				g = Integer.parseInt(tokenizer.nextToken()),
				b = Integer.parseInt(tokenizer.nextToken()),
				m = Integer.parseInt(tokenizer.nextToken());

			int rmax = getMaxFromList(in.readLine()),
				gmax = getMaxFromList(in.readLine()),
				bmax = getMaxFromList(in.readLine());

			output(m, rmax, gmax, bmax);
			out.println();
		}

		out.flush();
	}
}
