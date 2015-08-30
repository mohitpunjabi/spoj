import java.util.*;
import java.io.*;

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public static void output(ArrayList<Integer> a, int k) throws Exception {
		// TODO: Optimize!
		for(int c = a.size(); a.size() >= 3 && c >= 0; c--) {
			for(int i = 0; i < a.size() - 2; i++) {
				if(a.get(i).equals(a.get(i + 1)) && a.get(i + 1).equals(a.get(i + 2))) {
					a.remove(i);
					a.remove(i);
					a.remove(i);
					break;
				}
			}
		}

		out.print(a.size());
	}

	public static void main(String args[]) throws Exception {		
		int testCases = Integer.parseInt(in.readLine());
		for(int i = 0; i < testCases; i++) {
			StringTokenizer tokenizer = new StringTokenizer(in.readLine());
			int n = Integer.parseInt(tokenizer.nextToken()),
				k = Integer.parseInt(tokenizer.nextToken());
			ArrayList<Integer> a = new ArrayList<Integer>();

			tokenizer = new StringTokenizer(in.readLine());
			for(int j = 0; j < n; j++)
				a.add(Integer.parseInt(tokenizer.nextToken()));

			out.print("Case #" + (i + 1) + ": ");
			output(a, k);
			out.println();
		}

		out.flush();
	}
}
