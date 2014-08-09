import java.util.*;
import java.io.*;
import java.math.*;

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	private static BigInteger getLargestPowerOfTwo(BigInteger n) {
		BigInteger answer = new BigInteger("1");
		while(answer.compareTo(n) < 0)
			answer = answer.shiftLeft(1);

		return answer.shiftRight(1);
	}
	
	private static boolean richardWins(BigInteger n, boolean willRichardWin) {
		if(n.compareTo(BigInteger.ONE) == 0) return willRichardWin;

		return richardWins(n.subtract(getLargestPowerOfTwo(n)), !willRichardWin);
	}

	public static void output(BigInteger n) throws Exception {
		out.print(richardWins(n, true)? "Richard": "Louise");
	}

	public static void main(String args[]) throws Exception {		
		int testCases = Integer.parseInt(in.readLine());
		for(int i = 0; i < testCases; i++) {
			BigInteger n = new BigInteger(in.readLine());

			output(n);
			out.println();
		}

		out.flush();
	}
}
