import java.util.*;
import java.io.*;

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	private static int gcd(int a, int b) {
		if(b == 0) return a;
		return gcd(b, a % b);
	}

	private static int gcd(ArrayList<Integer> nums) {
		int d = nums.get(0);
		for(int num : nums) d = gcd(d, num);
		
		return d;
	}

	public static void output(ArrayList<Integer> nums) throws Exception {
		out.print((gcd(nums) == 1)? "YES": "NO");
	}

	public static void main(String args[]) throws Exception {		
		int testCases = Integer.parseInt(in.readLine());
		for(int i = 0; i < testCases; i++) {
			int n = Integer.parseInt(in.readLine());
			StringTokenizer tokenizer = new StringTokenizer(in.readLine());
			ArrayList<Integer> nums = new ArrayList<Integer>(n);
			for(int j = 0; j < n; j++)
				nums.add(Integer.parseInt(tokenizer.nextToken()));

			output(nums);
			out.println();
		}

		out.flush();
	}
}
