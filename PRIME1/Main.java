import java.util.*;
import java.io.*;

public class Main {

	private static ArrayList<Long> smallPrimes;
	private static final long LIMIT = (long) 1e9;
	
	private static void initSmallPrimes(int limit) {
		boolean[] isPrime = new boolean[limit];
		for(int i = 0; i < limit; isPrime[i++] = true);
		isPrime[0] = isPrime[1] = false;

		smallPrimes = new ArrayList<Long>();
		for(long i = 0; i < limit; i++) {
			if(isPrime[(int) i]) {
				for(long j = i * i; j < limit; j += i) isPrime[(int) j] = false;
				smallPrimes.add(i);
			}
		}
	}

	public static void output(long m, long n) {
		if(m < 2)  m = 2;
		if(n < m)  return;

 		boolean[] isPrime = new boolean[(int) (n - m + 1)];
		for(long i = m; i <= n; i++) isPrime[(int) (i - m)] = true;

		for(int i = 0; i < smallPrimes.size(); i++) {
			long prime = smallPrimes.get(i);
			long start = prime;			

			if(prime > n)		break;
			else if(prime >= m) start = 2 * prime;
			else	 		    start = m + ((prime - (m % prime)) % prime);

			for(long j = start; j <= n; j += prime) isPrime[(int) (j - m)] = false;
		}

		for(long i = m; i <= n; i++)
			if(isPrime[(int) (i - m)]) System.out.println(i);
	}

	public static void main(String args[]) throws Exception {		
		Scanner scanner = new Scanner(System.in);
		initSmallPrimes((int) Math.sqrt(LIMIT) + 10);

		long testCases = scanner.nextLong();
		for(int i = 0; i < testCases; i++) {
			long m = scanner.nextLong(),
				 n = scanner.nextLong();

			if(i > 0) System.out.println();
			output(m, n);
		}
	}

}
