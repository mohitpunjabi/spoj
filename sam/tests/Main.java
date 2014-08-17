import java.util.*;
import java.io.*;
import java.math.*;

class Matrix {

	public static final BigInteger MOD = new BigInteger("1000000007");
	public BigInteger[] values;

	public Matrix(BigInteger...vals) {
		values = new BigInteger[4];
		for(int i = 0; i < 4; i++) values[i] = vals[i];
	}

	public Matrix mul(Matrix to) {
		return new Matrix(values[0].multiply(to.values[0]).mod(MOD).add(values[1].multiply(to.values[2]).mod(MOD)).mod(MOD),
						  values[0].multiply(to.values[1]).mod(MOD).add(values[1].multiply(to.values[3]).mod(MOD)).mod(MOD),
						  values[2].multiply(to.values[0]).mod(MOD).add(values[3].multiply(to.values[2]).mod(MOD)).mod(MOD),
						  values[2].multiply(to.values[1]).mod(MOD).add(values[3].multiply(to.values[3]).mod(MOD)).mod(MOD));
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
		return values[0] + " " + values[1] + "\n" +
			   values[2] + " " + values[3];
	}
}

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public static BigInteger fib(BigInteger n) {
		Matrix f0 = new Matrix(BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO);
		Matrix fn = f0.pow(n);
		BigInteger two = BigInteger.valueOf((long) 2);
		return fn.values[0].multiply(two).mod(Matrix.MOD).subtract(fn.values[1]).mod(Matrix.MOD);
	}

	public static void output(BigInteger n) throws Exception {
		out.print(fib(n).toString());
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
