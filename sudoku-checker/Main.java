import java.util.*;
import java.io.*;

class Sudoku {

	private int[][] sudoku;
	private int n;

	public Sudoku(int n, String vals) {
		this.n = n;
		sudoku = new int[n * n][n * n];
		StringTokenizer tokenizer = new StringTokenizer(vals);
		for(int i = 0; i < n * n; i++) 
			for(int j = 0; j < n * n; j++) 
				sudoku[i][j] = Integer.parseInt(tokenizer.nextToken());
	}

	public boolean isValid() {
		for(int i = 0; i < n * n; i++) 
			if(!isRowValid(i) || !isColumnValid(i) || !isBlockValid(i))
				return false;
		
		return true;
	}

	private boolean isNumberValid(int num) {
		return num >= 1 && num <= n * n;
	}

	private boolean areGoodValues(int[] values) {
		boolean[] used = new boolean[n * n + 1];
		for(int i = 0; i < used.length; used[i++] = false);

		for(int i = 0; i < values.length; i++) {
			if(!isNumberValid(values[i]) || used[values[i]]) return false;
			used[values[i]] = true;
		}

		for(int i = 1; i <= n * n; i++)
			if(!used[i]) return false;

		return true;
	}

	private boolean isRowValid(int row) {
		return areGoodValues(sudoku[row]);
	}

	private boolean isBlockValid(int block) {
		int[] cvals = new int[n * n];
		int rowPad = n * (block % n),
			colPad = n * (block / n);
		int count = 0;
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				cvals[count] = sudoku[i + rowPad][j + colPad];
				count++;
			}
		}

		return areGoodValues(cvals);
	}

	private boolean isColumnValid(int col) {
		int[] cvals = new int[n * n];
		for(int i = 0; i < n * n; i++) cvals[i] = sudoku[i][col];
		return areGoodValues(cvals);
	}

}

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public static void output(int n, String vals) throws Exception {
		Sudoku sudoku = new Sudoku(n, vals);
		out.print(sudoku.isValid()? "Yes" : "No");
	}

	public static void main(String args[]) throws Exception {		
		int testCases = Integer.parseInt(in.readLine());
		for(int i = 0; i < testCases; i++) {
			int n = Integer.parseInt(in.readLine());
			StringBuffer vals = new StringBuffer();
			for(int j = 0; j < n * n; j++)
				vals.append(in.readLine()).append(" ");

			out.print("Case #" + (i + 1) + ": ");
			output(n, vals.toString());
			out.println();
		}

		out.flush();
	}
}
