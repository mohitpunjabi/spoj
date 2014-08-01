import java.util.*;
import java.io.*;

public class Main {

	private static ArrayList<Long> getSmallPrimes(int limit) {
		boolean[] isPrime = new boolean[limit];
		for(int i = 0; i < limit; isPrime[i++] = true);
		isPrime[0] = isPrime[1] = false;

		ArrayList<Long> smallPrimes = new ArrayList<Long>();
		for(long i = 0; i < limit; i++) {
			if(isPrime[(int) i]) {
				for(long j = i * i; j < limit; j += i) isPrime[(int) j] = false;
				smallPrimes.add(i);
			}
		}
		return smallPrimes;
	}

	private static int getFirstDivisorIndex(long n, ArrayList<Long> smallPrimes) {
		for(int i = 0; i < smallPrimes.size(); i++) {
			long nextPrime = smallPrimes.get(i);

			if(nextPrime * nextPrime > n) return smallPrimes.size();
			else if(n % nextPrime == 0)   return i;
		}

		return smallPrimes.size();
	}

	public static void output(long m, long n) {
		if(m == 1) m++;
		if(n < m)  return;

		int range = (int) (n - m + 1);
 		boolean[] isPrime = new boolean[range];
		for(int i = 0; i < range; isPrime[i++] = true);

		ArrayList<Long> smallPrimes = getSmallPrimes((int) Math.sqrt(n) + 1);
		for(long i = m; i <= n; i++) {
			if(isPrime[(int)(i - m)]) {
				int fdIndex = getFirstDivisorIndex(i, smallPrimes);
				long firstDivisor = i;
				if(fdIndex < smallPrimes.size()) {
					firstDivisor = smallPrimes.get(fdIndex);
					isPrime[(int)(i - m)] = false;
					smallPrimes.remove(fdIndex);
				}

				for(long j = i + firstDivisor; j <= n; j += firstDivisor)
					isPrime[(int)(j - m)] = false;
			}
		}

		for(int i = 0; i < range; i++)
			if(isPrime[i]) System.out.println((long) i + m);
	}

	public static void main(String args[]) throws Exception {		
		Scanner scanner = new Scanner(System.in);

		long testCases = scanner.nextLong();
		for(int i = 0; i < testCases; i++) {
			long m = scanner.nextLong(),
				 n = scanner.nextLong();

			output(m, n);
			System.out.println();
		}
	}

}
