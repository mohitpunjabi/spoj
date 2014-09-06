import java.util.*;
import java.io.*;
import java.math.*;

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	private static long PRIME_LIMIT = (long) 1e4;
	private static long BIG_FACTOR_TRY_LIMIT = (int) 20;
	private static ArrayList<Long> primes;
	
	static {
		boolean[] seive = new boolean[(int) PRIME_LIMIT + 1];
		for(int i = 0; i < PRIME_LIMIT; seive[i++] = true);
		seive[1] = seive[0] = false;
		for(long i = 0; i < PRIME_LIMIT; i++) 
			if(seive[(int) i]) 
				for(long j = i * i; j < PRIME_LIMIT; j += i)
					seive[(int) j] = false;

		primes = new ArrayList<Long>();
		for(int i = 0; i < PRIME_LIMIT; i++)
			if(seive[i]) primes.add((long) i);
	}


	private static ArrayList<BigInteger> getFactors(BigInteger n) {
		ArrayList<BigInteger> factors = new ArrayList<BigInteger>();

		for(long primeLong : primes) {
			BigInteger prime = BigInteger.valueOf(primeLong);
			while(n.mod(prime).compareTo(BigInteger.ZERO) == 0) {
				factors.add(prime);
				n = n.divide(prime);
				if(n.compareTo(prime) < 0) break;
			}
		}

		if(n.compareTo(BigInteger.ONE) > 0) factors.add(n);

		return factors;
	}

	private static BigInteger nextRandom(int digitLimit, Random rnd) {
		StringBuilder num = new StringBuilder();
		for(int i = 0; i < digitLimit; i++) {
			num.append("" + rnd.nextInt(10));
		}

		return new BigInteger(num.reverse().toString());
	}

	private static ArrayList<BigInteger> getBigFactors(BigInteger n) {
		ArrayList<BigInteger> bigFactors = new ArrayList<BigInteger>();
		Random rnd = new Random();

		int tries = 0;
		while(tries < BIG_FACTOR_TRY_LIMIT) {
			int nDigits = n.toString().length();
			if(nDigits < 30) break;
			BigInteger rand = nextRandom(Math.min(50, nDigits / 2), rnd);
			BigInteger g = n.gcd(rand);
			if(g.compareTo(BigInteger.ONE) > 0) {
				bigFactors.add(g);
				n = n.divide(g);
			}
			tries++;
		}

		if(n.compareTo(BigInteger.ONE) > 0) bigFactors.add(n);
		return bigFactors;
	}

	public static void output(String n) throws Exception {
		BigInteger num = new BigInteger(n);
		
		ArrayList<BigInteger> bigFactors = getBigFactors(num);
		ArrayList<BigInteger> factors = new ArrayList<BigInteger>();
		for(BigInteger bigFactor : bigFactors)
			factors.addAll(getFactors(bigFactor));

		out.println(factors.size());
		for(BigInteger factor : factors) out.println(factor);
	}

	public static void main(String args[]) throws Exception {		
		int testCases = Integer.parseInt(in.readLine());
		for(int i = 0; i < testCases; i++) {
			String n = in.readLine();
			output(n);
		}

		out.flush();
	}
}
