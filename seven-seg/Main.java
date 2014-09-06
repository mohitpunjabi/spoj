import java.util.*;
import java.io.*;


class Segment {
	
	public static  Segment[] DIGITS;
	public boolean[] states;

	static {
		DIGITS = new Segment[10];
		String[] statesStr = new String[]{"1111110", "0110000", "1101101", "1111001", "0110011", "1011011", "1011111", "1110000", "1111111", "1111011"};
		for(int i = 0; i < 10; i++)
			DIGITS[i] = new Segment(statesStr[i]);
	}

	public Segment(String state) {
		states = new boolean[7];
		char[] stateArr = state.toCharArray();
		for(int i = 0; i < 7; i++) states[i] = (stateArr[i] == '1');
	}

	public ArrayList<Integer> canBe(boolean[] broken) {
		ArrayList<Integer> canBes = new ArrayList<Integer>();
		for(int d = 0; d < 10; d++) {
			Segment digit = DIGITS[d];
			boolean canBeThis = true;
			for(int i = 0; i < 7; i++) {
				if(!broken[i]) {
					if(states[i] != digit.states[i]) {
						canBeThis = false;
						break;
					}
				}
			}

			if(canBeThis && !canBes.contains(d)) canBes.add(d);
		}

		return canBes;
	}

	public Segment breakSegs(boolean[] broken) {
		Segment brokenS = new Segment(toString());
		for(int i = 0; i < 7; i++)
			if(broken[i]) brokenS.states[i] = false;

		return brokenS;
	}

	public String toString() {
		StringBuffer str = new StringBuffer();
		for(int i = 0; i < 7; i++)
			str.append(states[i]? '1': '0');

		return str.toString();
	}
}

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public static boolean[] getBrokenSegs(ArrayList<Segment> segs) {
		boolean[] broken = new boolean[7];
		for(int i = 0; i < 7; broken[i++] = true);

		for(Segment seg : segs) {
			for(int i = 0; i < 7; i++) {
				if(seg.states[i]) broken[i] = false;
			}
		}

		return broken;
	}


	public static boolean canConfirmBroken(int poss, boolean[] broken, ArrayList<Segment> segs) {
		if(segs.size() > 2) return true;
		int brokenCount = 0;
		for(int i = 0; i < 7; i++) if(broken[i]) brokenCount++;
		if(brokenCount == 0) return true;

		return getConfirmBroken(poss, broken, segs) != null;
	}
	

	public static boolean[] getConfirmBroken(int poss, boolean[] broken, ArrayList<Segment> segs) {
		int[] conBroken = new int[7];
		for(int i = 0; i < 7; i++)
			conBroken[i] = (broken[i])? 2: 0;

		for(int i = 0; i < segs.size(); i++) {
			int curr = ((poss - i) % 10 + 10) % 10;
			for(int j = 0; j < 7; j++) {
				if(broken[j] && Segment.DIGITS[curr].states[j] && !segs.get(segs.size() - i - 1).states[j])
					conBroken[j] = 1;
				else if(Segment.DIGITS[curr].states[j] && segs.get(segs.size() - i - 1).states[j])
					conBroken[j] = 0;
			}
		}
		boolean[] confirmBroken = new boolean[7];
		for(int i = 0; i < 7; confirmBroken[i++] = false);
		
		for(int i = 0; i < 7; i++)
			if(conBroken[i] == 2) return null;

		for(int i = 0; i< 7; i++)
			confirmBroken[i] = (conBroken[i] == 1);
		return confirmBroken;
	}

	public static ArrayList<Integer> getNextPossible(ArrayList<Segment> segs, boolean[] broken) {
		ArrayList<ArrayList<Integer>> possCounter = new ArrayList<ArrayList<Integer>>();
		for(int i = 0; i < segs.size(); i++) 
			possCounter.add(segs.get(i).canBe(broken));

		ArrayList<Integer> possibles = new ArrayList<Integer>();

		for(int start : possCounter.get(0)) {
			boolean possible = true;
			for(int i = 1; i < possCounter.size(); i++) {
				int next = (start + (10 * i) - i) % 10;
				if(!possCounter.get(i).contains(next)) {
					possible = false;
					break;
				}
			}

			if(possible) possibles.add((start + (10 * segs.size()) - segs.size()) % 10);
		}
		
		return possibles;
	}

	public static void output(ArrayList<Segment> segs) throws Exception {
		boolean[] broken = getBrokenSegs(segs);
		ArrayList<Integer> possibles = getNextPossible(segs, broken);
		HashSet<String> possSet = new HashSet<String>();


		String onePossString = "";
		boolean confirmed = false;
		for(int poss : possibles) {
			String possString = "";
			boolean[] conBroken = (segs.size() > 2)? broken: getConfirmBroken(poss, broken, segs);
			if(conBroken != null) {
				confirmed = true;
				possString = Segment.DIGITS[poss].breakSegs(conBroken).toString();
				onePossString = possString;
				possSet.add(possString);
			}
			else break;
		}
		System.err.println("Found " + possSet.size() + " matches.");

		if(!confirmed || possSet.size() != 1)	out.print("ERROR!");
		else 									out.print(onePossString);
	}

	public static void main(String args[]) throws Exception {		
		int testCases = Integer.parseInt(in.readLine());
		for(int i = 0; i < testCases; i++) {
			StringTokenizer tokenizer = new StringTokenizer(in.readLine());
			int n = Integer.parseInt(tokenizer.nextToken());
			ArrayList<Segment> segs = new ArrayList<Segment>(n);
			for(int j = 0; j < n; j++) 
				segs.add(new Segment(tokenizer.nextToken()));

			System.err.println("Case #" + (i + 1) + ": ");
			out.print("Case #" + (i + 1) + ": ");
			output(segs);
			out.println();
		}

		out.flush();
	}
}
