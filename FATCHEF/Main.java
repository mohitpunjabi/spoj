import java.util.*;
import java.io.*;

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	private static class Bucket implements Comparable<Bucket> {
		public Integer fence;
		public char color;
		
		public Bucket(int fence, char color) {
			this.fence = fence;
			this.color = color;
		}

		public boolean equals(Object ob) {
			if(ob instanceof Bucket) {
				Bucket other = (Bucket) ob;
				return	other.fence == fence &&
						other.color == color;
			}
			return false;
		}

		public int compareTo(Bucket b) {
			return fence.compareTo(b.fence);
		}
	}

	private static final long MOD = (long) 1e9 + 9;

	public static void output(ArrayList<Bucket> buckets) throws Exception {
		long ways = 1;
		Bucket	curr = buckets.get(0),
				prev = null;
		for(int i = 1; i < buckets.size(); i++) {
			prev = curr;
			curr = buckets.get(i);
			if(prev.color != curr.color) {
				ways *= (curr.fence - prev.fence);
				ways %= MOD;
			}
		}

		out.print(ways);
	}

	public static void main(String args[]) throws Exception {		
		int testCases = Integer.parseInt(in.readLine());
		for(int i = 0; i < testCases; i++) {
			StringTokenizer tokenizer = new StringTokenizer(in.readLine());
			int n = Integer.parseInt(tokenizer.nextToken()),
				m = Integer.parseInt(tokenizer.nextToken());

			ArrayList<Bucket> buckets = new ArrayList<Bucket>(m);
			Bucket[] map = new Bucket[n + 1];
			for(int j = 0; j < m; j++) {
				char color = (char) in.read();
				in.read();
				int fence = Integer.parseInt(in.readLine());
				map[fence] = new Bucket(fence, color);
			}

			for(int j = 1; j <= n; j++)
				if(map[j] != null) buckets.add(map[j]);

			output(buckets);
			out.println();
		}

		out.flush();
	}
}
