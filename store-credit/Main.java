import java.util.*;
import java.io.*;

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	private static final int ITEM_LIMIT = 2001;

	public static void output(int credits, ArrayList<Integer> items) throws Exception {
		for(int i = 0; i < items.size(); i++) {
			for(int j = i + 1; j < items.size(); j++) {
				if(items.get(i) + items.get(j) == credits) {
					out.print((i + 1) + " " + (j + 1));
					return;
				}
			}
		}
	}

	public static void main(String args[]) throws Exception {		
		int testCases = Integer.parseInt(in.readLine());
		for(int t = 0; t < testCases; t++) {
			int credits = Integer.parseInt(in.readLine()),
				itemCount = Integer.parseInt(in.readLine());

			StringTokenizer tokenizer = new StringTokenizer(in.readLine());
			ArrayList<Integer> items = new ArrayList<Integer>(itemCount);

			for(int i = 0; i < itemCount; i++)
				items.add(Integer.parseInt(tokenizer.nextToken()));

			out.print("Case #" + (t + 1) + ": ");
			output(credits, items);
			out.println();
		}

		out.flush();
	}
}
