import java.util.*;
import java.io.*;

class Point {
	public int x, y;

	private static final int MAX = (int) 1e6 + 1;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int hashCode() {
		return x * MAX + y;
	}

	public boolean equals(Object ob) {
		if(ob instanceof Point) {
			Point other = (Point) ob;
			return other.x == x && other.y == y;
		}
		
		return false;
	}

	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	private static int pointsNeeded(Point p1, Point p2, HashSet<Point> points) {
		if(p1.x > p2.x) return pointsNeeded(p2, p1, points);

		Point	p3 = new Point(p2.x + p2.y - p1.y, p2.y - p2.x + p1.x),
				p4 = new Point(p1.x + p2.y - p1.y, p1.y - p2.x + p1.x);

//		System.err.println("Square: " + p1 + ", " + p2 + ", " + p3 + ", " + p4);

		int pointsNeeded = 2;
		if(points.contains(p3)) pointsNeeded--;
		if(points.contains(p4)) pointsNeeded--;
		if(pointsNeeded == 0) return 0;

		p3 = new Point(p2.x + p1.y - p2.y, p2.y - p1.x + p2.x);
		p4 = new Point(p1.x + p1.y - p2.y, p1.y - p1.x + p2.x);
//		System.err.println("Square: " + p1 + ", " + p2 + ", " + p3 + ", " + p4);

		int pointsNeeded2 = 2;
		if(points.contains(p3)) pointsNeeded2--;
		if(points.contains(p4)) pointsNeeded2--;

		return (pointsNeeded < pointsNeeded2)? pointsNeeded: pointsNeeded2;
	}

	public static void output(ArrayList<Point> points) throws Exception {
		HashSet<Point> pointSet = new HashSet<Point>(points);

		int pointsNeeded = (pointSet.size() == 0)? 4: 3;
		for(int i = 0; i < points.size(); i++) {
			for(int j = i + 1; j < points.size(); j++) {
				Point	p1 = points.get(i),
						p2 = points.get(j);

				if(p1 == p2) continue;
				int pNeeded = pointsNeeded(p1, p2, pointSet);
				if(pNeeded < pointsNeeded) pointsNeeded = pNeeded;
				if(pointsNeeded == 0) {
					out.print(0);
					return;
				}
			}
		}

		out.print(pointsNeeded);
	}

	public static void main(String args[]) throws Exception {		
		int n = Integer.parseInt(in.readLine());

		StringTokenizer tokenizer;
		ArrayList<Point> points = new ArrayList<Point>(n);
		for(int i = 0; i < n; i++) {
			tokenizer = new StringTokenizer(in.readLine());
			int x = Integer.parseInt(tokenizer.nextToken()),
				y = Integer.parseInt(tokenizer.nextToken());

			points.add(new Point(x, y));
		}

		output(points);
		out.println();
		out.flush();
	}
}
