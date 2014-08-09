import java.util.*;
import java.io.*;

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	private static int gcd(int a, int b) {
		if(b == 0) return a;
		return gcd(b, a % b);
	}

	private static int lcm(int a, int b) {
		return a * b / gcd(a, b);
	}

	public static void output(ArrayList<Integer> a) throws Exception {
		out.print(a.get(0) + " ");
		for(int i = 1; i < a.size(); i++)
			out.print(lcm(a.get(i - 1), a.get(i)) + " ");

		out.print(a.get(a.size() - 1));
	}

	public static void main(String args[]) throws Exception {		
		int testCases = Integer.parseInt(in.readLine());
		for(int i = 0; i < testCases; i++) {
			int n = Integer.parseInt(in.readLine());
			StringTokenizer tokenizer = new StringTokenizer(in.readLine());
			ArrayList<Integer> a = new ArrayList<Integer>(n);
			for(int j = 0; j < n; j++) 
				a.add(Integer.parseInt(tokenizer.nextToken()));

			output(a);
			out.println();
		}

		out.flush();
	}
}
