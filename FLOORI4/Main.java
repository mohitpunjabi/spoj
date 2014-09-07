import java.util.*;
import java.io.*;
import java.math.*;

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public static long term(int i, long n) {
		switch(i) {
			case 0: 
				return n;
			case 1:
				return n + 1;
			case 2:
				return (2 * n + 1);
			case 3:
				return (3 * n * (n + 1)) - 1;
		}
		return 0;
	}
	public static long term3(long n, long mod, boolean divideBy5) {
		if(n <= 1e8) {
			long t = term(3, n);
			if(divideBy5) t /= 5;
			return t % mod;
		}

		BigInteger N = BigInteger.valueOf(n),
				   N3 = BigInteger.valueOf(3).multiply(N),
				   Nplus1 = N.add(BigInteger.ONE),
				   term = new BigInteger("0");

		term = N3.multiply(Nplus1).subtract(BigInteger.ONE);
		if(divideBy5) term = term.divide(BigInteger.valueOf(5));
		term = term.mod(BigInteger.valueOf(mod));
		return term.longValue();
	}

	public static long sum(long to, long mod) {
		long base = to % 30;
		boolean[] divided = new boolean[6];
		int[] divisors = {2, 3, 5};
		for(int d : divisors) divided[d] = false;

		long sum = 1;
		for(int i = 0; i < 3; i++) {
			boolean added = false;
			long t = term(i, to);
			for(int d : divisors) {
				if(!divided[d] && term(i, base) % d == 0) {
					t /= d;
					divided[d] = true;
				}
			}
			sum = (sum * (t % mod)) % mod;
//			System.err.println("Multiplying " + t);
		}

//		System.err.println("Multiplying " + (term3(to, mod, !divided[5])));
		sum = (sum * term3(to, mod, !divided[5])) % mod;

//		System.err.println("Computed sum till " + to + ": " + ((mod + sum) % mod));
		return (mod + sum) % mod;
	}

	public static long sum(long from, long to, long mod) {
		return (mod + ((sum(to, mod) - sum(from - 1, mod)) % mod)) % mod;
	}

	public static long pow4(long n, long mod) {
		if(mod == 1) return 0;
		if(n == 0)	 return 1;

		n %= mod;
		n *= n;
		n %= mod;
		n *= n;

		return n % mod;
	}

	public static void output(long n, long mod) throws Exception {
	/*
		System.err.print("i: ");
		for(int i = 1; i <= n; i++)	System.err.printf("%3d", i);
		System.err.print("\nv: ");
		for(int i = 1; i <= n; i++) System.err.printf("%3d", (n / i));
		System.err.println();
	*/

		long i;
		long s = 0;
		for(i = 1; i < n; i++) {
			long lower = 1 + (n / (i + 1)),
				 higher = (n / i);
			if(lower >= higher) {
				i = higher;
				break;
			}

			long curr = (((n / lower) % mod) * (sum(lower, higher, mod) % mod)) % mod;
			s = (s + curr) % mod;
//			System.err.println("[" + (1 + (n / (i + 1))) + ", " + (n / i) + "]: " + curr);
		}

		for(int j = 1; j <= i; j++) {
//			System.err.println("Computed pow4 " + j + ": " + pow4(j, mod));
			long curr = (pow4(j, mod) * ((n / j) % mod)) % mod;
			s = (s + curr) % mod;
//			System.err.println(j + ": " + curr);
		}

		out.print(s);
	}

	public static void main(String args[]) throws Exception {		
		int testCases = Integer.parseInt(in.readLine());
		for(int i = 0; i < testCases; i++) {
			StringTokenizer tokenizer = new StringTokenizer(in.readLine());
			long	n = Long.parseLong(tokenizer.nextToken()),
					m = Long.parseLong(tokenizer.nextToken());

			output(n, m);
			out.println();
		}

		out.flush();
	}
}
