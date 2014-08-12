import java.util.*;
import java.io.*;

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	private static long[] height;

	static {
		height = new long[62];
		height[0] = 1;
		for(int i = 1; i <= 60; i++) {
			if(i % 2 == 1)	height[i] = height[i - 1] * 2;
			else			height[i] = height[i - 1] + 1;
		}
	}

	public static void output(int n) throws Exception {
		out.print(height[n]);
	}

	public static void main(String args[]) throws Exception {		
		int testCases = Integer.parseInt(in.readLine());
		for(int i = 0; i < testCases; i++) {
			int n = Integer.parseInt(in.readLine());

			output(n);
			out.println();
		}

		out.flush();
	}
}
