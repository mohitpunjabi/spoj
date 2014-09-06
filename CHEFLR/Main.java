import java.util.*;
import java.io.*;

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	private static final long MOD = ((long) 1e9) + 7;

	public static void output(char[] path) throws Exception {
		long node = 1;
		for(int i = 0; i < path.length; i++) {
			node *= 2;
			if(i % 2 == 0 && path[i] == 'r')
				node += 2;
			else if(i % 2 == 1) {
				if(path[i] == 'l') 	node--;
				else				node++;
			}

			node %= MOD;
		}

		out.print(node);
	}

	public static void main(String args[]) throws Exception {		
		int testCases = Integer.parseInt(in.readLine());
		for(int i = 0; i < testCases; i++) {
			char[] path = in.readLine().toCharArray();
			output(path);
			out.println();
		}

		out.flush();
	}
}
