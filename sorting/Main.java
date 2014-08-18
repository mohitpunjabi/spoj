import java.util.*;
import java.io.*;

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public static void output(ArrayList<Integer> books) throws Exception {
		ArrayList<Integer> 	alexBooks = new ArrayList<Integer>(),
							bobBooks = new ArrayList<Integer>();

		for(int book : books) {
			if(book % 2 != 0)	alexBooks.add(book);
			else			  	bobBooks.add(book);
		}

		Collections.sort(alexBooks);
		Collections.sort(bobBooks);

		int alexIndex = 0,
			bobIndex = bobBooks.size() - 1;

		for(int i = 0; i < books.size(); i++) {
			if(books.get(i) % 2 != 0)	books.set(i, alexBooks.get(alexIndex++));
			else						books.set(i, bobBooks.get(bobIndex--));

			out.print(books.get(i) + " ");
		}
	}

	public static void main(String args[]) throws Exception {		
		int testCases = Integer.parseInt(in.readLine());
		for(int i = 0; i < testCases; i++) {
			int n = Integer.parseInt(in.readLine());
			StringTokenizer tokenizer = new StringTokenizer(in.readLine());
			ArrayList<Integer> books = new ArrayList<Integer>(n);

			for(int j = 0; j < n; j++)
				books.add(Integer.parseInt(tokenizer.nextToken()));

			out.print("Case #" + (i + 1) + ": ");
			output(books);
			out.println();
		}

		out.flush();
	}
}
