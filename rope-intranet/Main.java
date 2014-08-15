import java.util.*;
import java.io.*;

class Wire {
	public int start;
	public int end;

	public Wire(int start, int end) {
		this.start = start;
		this.end = end;
	}

}

class WireComparator implements Comparator<Wire> {

	public int compare(Wire w1, Wire w2) {
		return ((Integer) w1.start).compareTo((Integer) w2.start);
	}

}

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	private static final int N_LIMIT = 10001;

	public static void output(ArrayList<Wire> wires) throws Exception {
		Collections.sort(wires, new WireComparator());
		int maxEnd = -1;
		int[] wireEnds = new int[N_LIMIT];
		for(int i = 0; i < N_LIMIT; wireEnds[i++] = 0);

		int intersections = 0;
		for(Wire wire : wires) {
			for(int i = wire.end + 1; i <= maxEnd; i++) intersections += wireEnds[i];
			wireEnds[wire.end]++;
			if(wire.end > maxEnd) maxEnd = wire.end;
		}

		out.print(intersections);
	}

	public static void main(String args[]) throws Exception {		
		int testCases = Integer.parseInt(in.readLine());
		for(int i = 0; i < testCases; i++) {
			int n = Integer.parseInt(in.readLine());

			ArrayList<Wire> wires = new ArrayList<Wire>(n);

			for(int j = 0; j < n; j++) {
				StringTokenizer tokenizer = new StringTokenizer(in.readLine());
				int a = Integer.parseInt(tokenizer.nextToken()),
					b = Integer.parseInt(tokenizer.nextToken());
				wires.add(new Wire(a, b));
			}

			out.print("Case #" + (i + 1) + ": ");
			output(wires);
			out.println();
		}

		out.flush();
	}
}
