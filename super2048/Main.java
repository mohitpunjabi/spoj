import java.util.*;
import java.io.*;

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));


	private static void shiftRowLeft(int[] row, int n) {
		int fillIndex = 0;
		int curr = 0;
		while(curr < n) {
			if(row[curr] > 0) {
				row[fillIndex] = row[curr];
				if(fillIndex != curr) row[curr] = 0;
				fillIndex++;
			}
			curr++;
		}
	}

	private static void moveRowLeft(int[] row, int n) {
		shiftRowLeft(row, n);

		for(int i = 0; i < n - 1; i++) {
			if(row[i + 1] == row[i]) {
				row[i] *= 2;
				row[i + 1] = 0;
			}
		}

		shiftRowLeft(row, n);
	}

	private static void shiftRowRight(int[] row, int n) {
		int fillIndex = n - 1;
		int curr = n - 1;
		while(curr >= 0) {
			if(row[curr] > 0) {
				row[fillIndex] = row[curr];
				if(fillIndex != curr) row[curr] = 0;
				fillIndex--;
			}
			curr--;
		}
	}

	private static void moveRowRight(int[] row, int n) {
	
		shiftRowRight(row, n);
		for(int i = n - 1; i > 0; i--) {
			if(row[i - 1] == row[i]) {
				row[i] *= 2;
				row[i - 1] = 0;
			}
		}

		shiftRowRight(row, n);
	}

	public static void output(int[][] grid, int n, String dir) throws Exception {
		for(int i = 0; i < n; i++) {
			if(dir.equals("right") || dir.equals("down"))
				moveRowRight(grid[i], n);
			else
				moveRowLeft(grid[i], n);
		}

		for(int i = 0; i < n; i++) {
			out.println();
			for(int j = 0; j < n; j++) {
				if(dir.equals("left") || dir.equals("right"))
					out.print(grid[i][j] + " ");
				else
					out.print(grid[j][i] + " ");
			}
		}
	}

	public static void main(String args[]) throws Exception {		
		int testCases = Integer.parseInt(in.readLine());
		for(int i = 0; i < testCases; i++) {
			StringTokenizer tokenizer = new StringTokenizer(in.readLine());
			int n = Integer.parseInt(tokenizer.nextToken());
			String dir = tokenizer.nextToken();

			int[][] grid = new int[n][n];
			for(int j = 0; j < n; j++) {
				tokenizer = new StringTokenizer(in.readLine());
				for(int k = 0; k < n; k++) {
					if(dir.equals("left") || dir.equals("right"))
						grid[j][k] = Integer.parseInt(tokenizer.nextToken());
					else
						grid[k][j] = Integer.parseInt(tokenizer.nextToken());
				}
			}

			out.print("Case #" + (i + 1) + ": ");
			output(grid, n, dir);
			out.println();
		}

		out.flush();
	}
}
