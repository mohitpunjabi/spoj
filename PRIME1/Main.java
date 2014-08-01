import java.util.*;
import java.io.*;

public class Main {
	private static final int SMALL_PRIME_LIMIT = (int) Math.sqrt(1e9) + 1;
	private static final int RANGE = (int) 1e6;
	private static PrintWriter output;
	private static ArrayList<Long> smallPrimes;

	private static ArrayList<Long> getSmallPrimes(int limit) {
		boolean[] isPrime = new boolean[limit];
		for(int i = 0; i < limit; isPrime[i++] = true);
		isPrime[0] = isPrime[1] = false;

		ArrayList<Long> smallPrimes = new ArrayList<Long>(SMALL_PRIME_LIMIT / 10);
		for(long i = 0; i < limit; i++) {
			if(isPrime[(int) i]) {
				for(long j = i * i; j < limit; j += i) isPrime[(int) j] = false;
				smallPrimes.add(i);
			}
		}
		return smallPrimes;
	}

	private static long getFirstDivisor(long n) {
		for(int i = 0; i * i <= n && i < smallPrimes.size(); i++) {
			long nextPrime = smallPrimes.get(i);
			if(n % nextPrime == 0) return nextPrime;
		}

		return n;
	}

	public static void output(long m, long n) {
		if(m == 1) m++;
		if(n < m)  return;

		int range = (int) (n - m + 1);
 		boolean[] isPrime = new boolean[range];
		for(int i = 0; i < range; isPrime[i++] = true);

		for(long i = m; i <= n; i++) {
			if(isPrime[(int)(i - m)]) {
				long firstDivisor = getFirstDivisor(i);
				isPrime[(int)(i - m)] = (firstDivisor == i);
				for(long j = i + firstDivisor; j <= n; j += firstDivisor) isPrime[(int)(j - m)] = false;
			}
		}

		for(int i = 0; i < range; i++)
			if(isPrime[i]) output.println((long) i + m);
	}

	public static void main(String args[]) throws Exception {		
		Scanner scanner = new Scanner(System.in);
		output = new PrintWriter(System.out);
		smallPrimes = getSmallPrimes(SMALL_PRIME_LIMIT);

		long testCases = scanner.nextLong();

		for(int i = 0; i < testCases; i++) {
			long m = scanner.nextLong(),
				 n = scanner.nextLong();

			output(m, n);
			System.out.println();
		}
	}

}
