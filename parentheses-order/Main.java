import java.util.*;
import java.io.*;

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	private static int[] getBits(int k, int digits) {
		int[] bits = new int[digits];
		for(int i = digits - 1; i >= 0; i--) {
			bits[i] = k % 2;
			k /= 2;
		}
		return bits;
	}

	private static boolean isValid(int[] paren) {
		int n = paren.length;
		int balance = 0;
		for(int i = 0; i < n; i++) {
			if(paren[i] == 0) 	balance++;
			else 				balance--;
			if(balance < 0) return false;
		}
		return balance == 0;
	}

	private static String getParenString(int[] bits) {
		StringBuilder sb = new StringBuilder();
		for(int bit : bits) {
			if(bit == 0) sb.append('(');
			else		 sb.append(')');
		}
		return sb.toString();
	}

	public static void output(int n, int k) throws Exception {
		// No time to think. Use brute force!
		ArrayList<String> ordered = new ArrayList<String>();	
		int max = 1 << (2 * n);
		for(int i = 0; i <= max; i++) {
			int[] bits = getBits(i, 2 * n);
			if(isValid(bits)) ordered.add(getParenString(bits));
		}
	
		Collections.sort(ordered);
		if(k > ordered.size())	out.print("Doesn't Exist!");
		else					out.print(ordered.get(k - 1));
	}

	public static void main(String args[]) throws Exception {		
		int testCases = Integer.parseInt(in.readLine());
		for(int i = 0; i < testCases; i++) {
			StringTokenizer tokenizer = new StringTokenizer(in.readLine());
			int n = Integer.parseInt(tokenizer.nextToken()),
				k = Integer.parseInt(tokenizer.nextToken());

			out.print("Case #" + (i + 1) + ": ");
			output(n, k);
			out.println();
		}

		out.flush();
	}
}
