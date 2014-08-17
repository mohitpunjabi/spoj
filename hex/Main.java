import java.util.*;
import java.io.*;


class Hex {

	Cell[][] board;

	ArrayList<Cell> blues;
	ArrayList<Cell> reds;
	int n;

	class Cell {
		public int x;
		public int y;
		public char val;
		private Hex board;
		private Cell parent;
		public boolean visited;

		public Cell(int x, int y, char val, Hex board) {
			this.x = x;
			this.y = y;
			this.val = val;
			this.board = board;
			visited = false;
		}

		public boolean isEmpty() {
			return val == '.';
		}

		public boolean isEnd() {
			if(val == 'R') return x == n - 1;
			if(val == 'B') return y == n - 1;

			return false;
		}

		public String toString() {
			return "(" + x + ", " + y + ") " + val;
		}

		public ArrayList<Cell> neighbours() {
			ArrayList<Cell> nbours = new ArrayList<Cell>(6);

			int[][] points = new int[][]{	{x - 1, y},
											{x, y - 1},
											{x + 1, y},
											{x, y + 1},
											{x + 1, y - 1},
											{x - 1, y + 1}};

			for(int i = 0; i < 6; i++) 
				if(isIn(points[i][0], points[i][1]))
					nbours.add(board.get(points[i][0], points[i][1]));

			return nbours;
		}
	}


	public Hex(int n, String vals) {
		blues = new ArrayList<Cell>();
		reds = new ArrayList<Cell>();
		this.n = n;
		board = new Cell[n][n];
		char[] valArr = vals.toCharArray();
		int count = 0;
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				board[i][j] = new Cell(i, j, valArr[count++], this);
				if(board[i][j].val == 'B')		blues.add(board[i][j]);
				else if(board[i][j].val == 'R')	reds.add(board[i][j]);
			}
		}
	}

	public int size() {
		return n;
	}

	public Cell get(int x, int y) {
		return board[x][y];
	}


	private boolean isBiconnected(char state) {
		ArrayList<Cell> cells = (state == 'B')? blues: reds;

		for(Cell cell : cells) {
			cell.val = '.';
			int paths = getConnectedPaths(state);
			if(paths == 0) return false;
			cell.val = state;
		}

		return true;
	}

	private int getConnectedPaths(char state) {
		LinkedList<Cell> queue = new LinkedList<Cell>();
		
		for(int i = 0; i < n; i++)
			for(int j = 0; j < n; j++)
				board[i][j].visited = false;

		System.err.println("Getting connected " + state + " paths");

		if(state == 'B') {
			for(int i = 0; i < n; i++) {
				if(get(i, 0).val == state) {
					queue.add(get(i, 0));
					System.err.println("Added to queue: " + get(i, 0).toString());
				}
			}
		}
		else if(state == 'R') {
			for(int i = 0; i < n; i++) {
				if(get(0, i).val == state) {
					queue.add(get(0, i));
					System.err.println("Added to queue: " + get(0, i).toString());
				}
			}
		}


		int pathCount = 0;
		while(queue.size() > 0) {
			Cell current = queue.remove();
			if(!current.visited) {
				if(current.val == state && current.isEnd()) {
					System.err.println("Found end: " + current.toString());
					pathCount++;
				}
				for(Cell nbour : current.neighbours())
					if(current.val == state && !nbour.visited) queue.add(nbour);
				current.visited = true;
			}
		}

		System.err.println("Found " + pathCount + " connected ends.");
		return pathCount;
	}

	public int getCount(char state) {
		int count = 0;
		for(int i = 0; i < n; i++)
			for(int j = 0; j < n; j++)
				if(get(i, j).val == state) count++;

		return count;
	}

	public String getStateString() {
		int bcount = getCount('B'),
			rcount = getCount('R');

		if(Math.abs(bcount - rcount) > 1)
			return "Impossible";

		int bConnections = getConnectedPaths('B'),
			rConnections = getConnectedPaths('R');

		if(bConnections >= 1 && rConnections >= 1)	return "Impossible";

		if(bConnections == 1)						return "Blue wins";
		if(rConnections == 1)						return "Red wins";
		if(bConnections > 1) {
			if(isBiconnected('B'))					return "Impossible";
													return "Blue wins";
		}
		if(rConnections > 1) {
			if(isBiconnected('R'))					return "Impossible";
													return "Red wins";
		}

		return "Nobody wins";
	}

	public boolean isIn(int x, int y) {
		return x >= 0 && y >= 0 && x < n && y < n;
	}
}


public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public static void output(Hex hex) throws Exception {
		out.print(hex.getStateString());
	}

	public static void main(String args[]) throws Exception {		
		int testCases = Integer.parseInt(in.readLine());
		for(int i = 0; i < testCases; i++) {
			int n = Integer.parseInt(in.readLine());

			StringBuffer board = new StringBuffer();
			for(int j = 0; j < n; j++)
				board.append(in.readLine());

			Hex hex = new Hex(n, board.toString());

			System.err.println("Case #" + (i + 1) + ": ");
			out.print("Case #" + (i + 1) + ": ");
			output(hex);
			out.println();
		}

		out.flush();
	}
}
