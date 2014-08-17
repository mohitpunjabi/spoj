import java.util.*;
import java.io.*;
import java.math.*;

class RationalNumber {
	public BigInteger p;
	public BigInteger q;
	private BigInteger n;

	public RationalNumber(BigInteger p, BigInteger q) {
		this.p = p;
		this.q = q;
		this.n = calcN(p, q);
	}

	public RationalNumber(BigInteger p, BigInteger q, BigInteger n) {
		this.p = p;
		this.q = q;
		this.n = n;
	}

	public static RationalNumber numberAt(BigInteger n) {
		if(n.compareTo(BigInteger.ONE) == 0)
			return new RationalNumber(BigInteger.ONE, BigInteger.ONE, BigInteger.ONE);

		RationalNumber parent = numberAt(n.divide(BigInteger.valueOf(2)));
		if(n.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO))
			return new RationalNumber(parent.p, parent.p.add(parent.q), n);
		return new RationalNumber(parent.p.add(parent.q), parent.q, n);
	}

	private BigInteger calcN(BigInteger p, BigInteger q) {
		if(p.equals(q)) return BigInteger.ONE;
		if(p.compareTo(q) > 0) return calcN(p.subtract(q), q).multiply(BigInteger.valueOf(2)).add(BigInteger.ONE);
		return calcN(p, q.subtract(p)).multiply(BigInteger.valueOf(2));
	}

	public BigInteger getN() {
		return n;
	}

	public String toString() {
		return p.toString() + " " + q.toString();
	}

}

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public static void output(int id, RationalNumber num) throws Exception {
		out.print((id == 1)? num.toString(): ("" + num.getN()));
	}

	public static void main(String args[]) throws Exception {		
		int testCases = Integer.parseInt(in.readLine());
		for(int i = 0; i < testCases; i++) {
			StringTokenizer tokenizer = new StringTokenizer(in.readLine());
			int id = Integer.parseInt(tokenizer.nextToken());

			RationalNumber num = null;

			if(id == 1) {
				BigInteger n = new BigInteger(tokenizer.nextToken());
				num = RationalNumber.numberAt(n);
			}
			else {
				BigInteger p = new BigInteger(tokenizer.nextToken()),
						   q = new BigInteger(tokenizer.nextToken());

				num = new RationalNumber(p, q);
			}

			out.print("Case #" + (i + 1) + ": ");
			output(id, num);
			out.println();
		}

		out.flush();
	}
}
