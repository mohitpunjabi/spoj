import java.util.*;
import java.io.*;
import java.math.*;

class Matrix {

	public static final BigInteger MOD = new BigInteger("1000000007");
	public BigInteger[] values;

	public Matrix(BigInteger...vals) {
		values = new BigInteger[9];
		for(int i = 0; i < 9; i++) values[i] = vals[i];
	}

	public Matrix mul(Matrix to) {
		return new Matrix(values[0].multiply(to.values[0]).add(values[1].multiply(to.values[3])).add(values[2].multiply(to.values[6])).mod(MOD),
						  values[0].multiply(to.values[1]).add(values[1].multiply(to.values[4])).add(values[2].multiply(to.values[7])).mod(MOD),
						  values[0].multiply(to.values[2]).add(values[1].multiply(to.values[5])).add(values[2].multiply(to.values[8])).mod(MOD),
						  values[3].multiply(to.values[0]).add(values[4].multiply(to.values[3])).add(values[5].multiply(to.values[6])).mod(MOD),
						  values[3].multiply(to.values[1]).add(values[4].multiply(to.values[4])).add(values[5].multiply(to.values[7])).mod(MOD),
						  values[3].multiply(to.values[2]).add(values[4].multiply(to.values[5])).add(values[5].multiply(to.values[8])).mod(MOD),
						  values[6].multiply(to.values[0]).add(values[7].multiply(to.values[3])).add(values[8].multiply(to.values[6])).mod(MOD),
						  values[6].multiply(to.values[1]).add(values[7].multiply(to.values[4])).add(values[8].multiply(to.values[7])).mod(MOD),
						  values[6].multiply(to.values[2]).add(values[7].multiply(to.values[5])).add(values[8].multiply(to.values[8])).mod(MOD));
	}

	public Matrix pow(BigInteger exp) {
		BigInteger two = BigInteger.valueOf((long) 2);
		if(exp.compareTo(BigInteger.ONE) == 0) return this;
		Matrix p = this.pow(exp.divide(two));
		p = p.mul(p);

		if(exp.mod(two).compareTo(BigInteger.ONE) == 0)
			return this.mul(p);

		return p;
	}

	public String toString() {
		return values[0] + " " + values[1] + " " + values[2] +  "\n" +
			   values[3] + " " + values[4] + " " + values[5] +  "\n" +
			   values[6] + " " + values[7] + " " + values[8];
	}
}

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public static void output(Matrix mat, BigInteger n) {
//		out.println(mat.toString());
		Matrix matn = mat.pow(n);
//		out.println(matn.toString());
		BigInteger two = BigInteger.valueOf((long) 2);
		BigInteger one = BigInteger.valueOf((long) 1);


		BigInteger an = matn.values[0].multiply(one).add(matn.values[1].multiply(two)).add(matn.values[2].multiply(one)).mod(Matrix.MOD);
		BigInteger bn = matn.values[3].multiply(one).add(matn.values[4].multiply(two)).add(matn.values[5].multiply(one)).mod(Matrix.MOD);

		out.print(an.toString() + " " + bn.toString());
	}

	public static void main(String args[]) throws Exception {		
		StringTokenizer tokenizer = new StringTokenizer(in.readLine());
		int testCases = Integer.parseInt(tokenizer.nextToken());
		for(int i = 0; i < testCases; i++) {
			tokenizer = new StringTokenizer(in.readLine());
			BigInteger a1 = new BigInteger(tokenizer.nextToken()),
					   b1 = new BigInteger(tokenizer.nextToken()),
					   c1 = new BigInteger(tokenizer.nextToken()),
					   a2 = new BigInteger(tokenizer.nextToken()),
					   b2 = new BigInteger(tokenizer.nextToken()),
					   c2 = new BigInteger(tokenizer.nextToken()),
					   n = new BigInteger(tokenizer.nextToken());

			Matrix mat = new Matrix(a1, b1, c1, a2, b2, c2, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE);
			output(mat, n.subtract(BigInteger.ONE));
			out.println();
		}

		out.flush();
	}
}
