import java.util.*;
import java.io.*;

class Maze {

	private char[][] maze;
	private int n;

	public Maze(int n, String mazeString) {
		maze = new char[n + 1][n + 1];
		this.n = n;
		char[] mazeArr = mazeString.toCharArray();
		int counter = 0;
		for(int i = 1; i <= n; i++) {
			for(int j = 1; j <= n; j++) {
				maze[i][j] = mazeArr[counter++];
			}
		}
	}

	public boolean isWall(int i, int j) {
		return !isInside(i, j) || maze[i][j] == '#';
	}

	public boolean isInside(int i, int j) {
		return i > 0 && i <= n && j > 0 && j <= n;
	}

	public int size() {
		return n;
	}
}

class Edison {

	private int x;
	private int y;
	private int direction;
	private Maze maze;

	public static final int N = 0,
							E = 1,
							S = 2,
							W = 3;

	private StringBuffer moves;

	public Edison(Maze maze, int startX, int startY, int direction) {
		x = startX;
		y = startY;
		this.maze = maze;
		this.direction = direction;
		moves = new StringBuffer();
	}

	private void turnLeft() {
		direction = (direction + 3) % 4;
	}

	private void turnRight() {
		direction = (direction + 1) % 4;
	}

	private boolean isWallOnLeft() {
		if(direction == N) return maze.isWall(x, y - 1);
		if(direction == S) return maze.isWall(x, y + 1);
		if(direction == W) return maze.isWall(x + 1, y);
		if(direction == E) return maze.isWall(x - 1, y);
		return false;
	}

	private boolean isWallAhead() {
		if(direction == W) return maze.isWall(x, y - 1);
		if(direction == E) return maze.isWall(x, y + 1);
		if(direction == S) return maze.isWall(x + 1, y);
		if(direction == N) return maze.isWall(x - 1, y);
		return false;
	}

	private char dirChar(int direction) {
		if(direction == N) return 'N';
		if(direction == S) return 'S';
		if(direction == E) return 'E';
		if(direction == W) return 'W';

		return ' ';
	}

	private void moveForward() {
		if(direction == W) y--;
		if(direction == E) y++;
		if(direction == S) x++;
		if(direction == N) x--;
		moves.append(dirChar(direction));
	}

	public String getMoves() {
		return moves.toString();
	}

	public boolean moveTo(int endX, int endY) {
		int moveCount = 0;
		int consecutiveTurns = 0;

	/*	
		direction = S;
		while(!isWallAhead()) {
			moveForward();
			System.err.println(toString());
		}

		if(true) return false;
*/
		while(!isWallOnLeft()) turnLeft();

		while(x != endX || y != endY) {
			System.err.println(toString());
			if(moveCount >= 10000) return false;
			if(consecutiveTurns == 4) return false;

			if(!isWallOnLeft()) {
				turnLeft();
				moveForward();
				moveCount++;
				consecutiveTurns = 0;
				continue;
			}

			if(!isWallAhead()) {
				moveForward();
				consecutiveTurns = 0;
				moveCount++;
				continue;
			}

			turnRight();
			consecutiveTurns++;
		}

		return true;
	}


	public String toString() {
		StringBuffer mString = new StringBuffer();
		for(int i = 1; i <= maze.size(); i++) {
			for(int j = 1; j <= maze.size(); j++) {
				if(maze.isWall(i, j)) 		mString.append('#');
				else if(i == x && j == y)   mString.append(dirChar(direction));
				else						mString.append('.');
			}
			mString.append('\n');
		}

		return mString.toString();
	}

}

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public static void output(Maze maze, int startX, int startY, int endX, int endY) throws Exception {
		Edison edison = new Edison(maze, startX, startY, Edison.N);
		if(edison.moveTo(endX, endY)) {
			String moves = edison.getMoves();
			out.println(moves.length());
			out.print(moves);
		}
		else {
			out.print("Edison ran out of energy.");
		}
	}

	public static void main(String args[]) throws Exception {		
		int testCases = Integer.parseInt(in.readLine());
		for(int i = 0; i < testCases; i++) {
			int n = Integer.parseInt(in.readLine());

			StringBuffer mazeString = new StringBuffer();
			for(int j = 0; j < n; j++) 
				mazeString.append(in.readLine());

			Maze maze = new Maze(n, mazeString.toString());

			StringTokenizer tokenizer = new StringTokenizer(in.readLine());
			int sx = Integer.parseInt(tokenizer.nextToken()),
				sy = Integer.parseInt(tokenizer.nextToken()),
				ex = Integer.parseInt(tokenizer.nextToken()),
				ey = Integer.parseInt(tokenizer.nextToken());

			out.print("Case #" + (i + 1) + ": ");
			output(maze, sx, sy, ex, ey);
			out.println();
		}

		out.flush();
	}
}
