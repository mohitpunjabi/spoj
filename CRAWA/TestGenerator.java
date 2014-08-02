import java.io.*;

public class TestGenerator {

	private static void printTest(long testLimit) {
		long testCount = (testLimit * (testLimit + 1)) / 2;
		System.out.println(testCount);
		int direction = 0;
		long x = 0, y = 0;

		for(long i = 1; i <= testLimit; i++) {
			for(long j = 0; j < i; j++) {
				if(direction == 0)		x++;
				else if(direction == 1)	y++;
				else if(direction == 2) x--;
				else if(direction == 3) y--;

				System.out.println(x + " " + y);
			}

			direction = (direction + 1) % 4;
		}
	}

	private static void printSolution(long testLimit) {
		long testCount = (testLimit * (testLimit + 1)) / 2;
		for(long i = 0; i < testCount; i++) System.out.println("YES");
	}

	public static void main(String args[]) {
		long testLimit = Long.parseLong(args[0]);

		if(args[1].equals("test")) printTest(testLimit);
		else					   printSolution(testLimit);
	}

}
