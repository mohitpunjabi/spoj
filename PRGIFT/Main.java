import java.util.*;
import java.io.*;

public class Main {

	private static boolean isSegmentPossible(ArrayList<Integer> nums, int k) {
		int evenCount = 0;
		for(int i = 0; i < nums.size(); i++) {
			if(nums.get(i) % 2 == 0) evenCount++;
			if(k > 0 && evenCount >= k) return true;
		}

		if(k == 0) return evenCount < nums.size();
		return false;
	}

	public static void output(ArrayList<Integer> nums, int k) {
		System.out.print(isSegmentPossible(nums, k)? "YES": "NO");
	}

	public static void main(String args[]) throws Exception {		
		Scanner scanner = new Scanner(System.in);

		int testCases = scanner.nextInt();
		for(int i = 0; i < testCases; i++) {
			int n = scanner.nextInt(),
				k = scanner.nextInt();

			ArrayList<Integer> nums = new ArrayList<Integer>(n);
			for(int j = 0; j < n; j++) nums.add(scanner.nextInt());

			output(nums, k);
			System.out.println();
		}
	}

}
