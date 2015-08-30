import java.util.*;
import java.io.*;

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public static int nextVillagerAfter(ArrayList<Integer> count, int start) {
		for(int i = start; i < count.size(); i++)
			if(count.get(i) > 0) return i;

		return -1;
	}

	public static int nextDinosaurAfter(ArrayList<Integer> count, int start) {
		for(int i = start; i < count.size(); i++)
			if(count.get(i) < 0) return i;

		return -1;
	}

	public static void output(ArrayList<Integer> count) throws Exception {
		int currVillager = nextVillagerAfter(count, 0),
			currDinosaur = nextDinosaurAfter(count, 0);

		long grassNeeded = 0;
		while(currVillager >=0 && currDinosaur >= 0) {
			int v = count.get(currVillager),
				d = -count.get(currDinosaur);
			grassNeeded += Math.abs(currVillager - currDinosaur) * Math.min(v, d);
			count.set(currVillager, Math.max(0, v - d));
			count.set(currDinosaur, Math.min(0, v - d));
			currVillager = nextVillagerAfter(count, currVillager);
			currDinosaur = nextDinosaurAfter(count, currDinosaur);
		}

		out.print(grassNeeded);
	}

	public static void main(String args[]) throws Exception {		
		int testCases = Integer.parseInt(in.readLine());
		for(int i = 0; i < testCases; i++) {
			int n = Integer.parseInt(in.readLine());

			StringTokenizer tokenizer = new StringTokenizer(in.readLine());
			ArrayList<Integer> count = new ArrayList<Integer>(n);
			
			for(int j = 0; j < n; j++)
				count.add(Integer.parseInt(tokenizer.nextToken()));

			output(count);
			out.println();
		}

		out.flush();
	}
}
