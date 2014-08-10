import java.util.*;
import java.io.*;

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public static void output(ArrayList<Long> a) throws Exception {
		Shuffler shuffler = new Shuffler(new ArrayList<Long>(a));
		OldShuffler oldShuffler = new OldShuffler(new ArrayList<Long>(a));
		shuffler.minimizeScore();
		oldShuffler.minimizeScore();

		ArrayList<Shuffle> shuffles = null;
		if(shuffler.getScore() > oldShuffler.minimizeScore())	shuffles = shuffler.getShuffles();
		else													shuffles = oldShuffler.getShuffles();

		if(shuffles != null) {
			out.println(shuffles.size());
			for(Shuffle shuffle : shuffles) out.println(shuffle.toString());
		}
		else
			out.println("0");
	}

	public static void main(String args[]) throws Exception {		
		int n = Integer.parseInt(in.readLine());
		StringTokenizer tokenizer = new StringTokenizer(in.readLine());
		ArrayList<Long> a = new ArrayList<Long>();
		for(int i = 0; i < n; i++) 
			a.add(Long.parseLong(tokenizer.nextToken()));

		output(a);
		out.flush();
	}
}

class Shuffle {
	public int from;
	public int to;

	public Shuffle(int from, int to) {
		this.from = from;
		this.to = to;
	}

	public String toString() {
		return from + " " + to;
	}
}

class Shuffler { 
	private ArrayList<Long> a;
	private ArrayList<Shuffle> shuffles;
	private int shuffleRangeSum;
	private long score;
	private long initialScore;
	private Random rand;

	public Shuffler(ArrayList<Long> a) {
		this.a  = a;
		shuffles = new ArrayList<Shuffle>();
		shuffleRangeSum = 0;
		initialScore = computeScore();
		score = initialScore;
		rand = new Random();
	}

	public boolean isShufflePossible(Shuffle shuffle) {
		return shuffle != null &&
			   score != 0 &&
			   shuffleRangeSum + (shuffle.to - shuffle.from + 1) < 2 * a.size();
	}

	public void minimizeScore() {
		Shuffle shuffle = getNextShuffle();
		int mid = (a.size() / 2);
		int midIndexNotChecked = mid;
		while(true) {
			checkAndAddShuffle(shuffle);
			if(!trySwap(mid, midIndexNotChecked++)) return;
			shuffle = getNextShuffle();
		}
	}

	private boolean trySwap(int i1, int i2) {
		if(i1 == i2) 	return true;
		if(i1 > i2)		return trySwap(i2, i1);

		Shuffle shuffle = new Shuffle(i1 + 1, i2 + 1);
		if(!isShufflePossible(shuffle)) return false;
		shuffles.add(shuffle);
		shuffleRangeSum += shuffle.to - shuffle.from  + 1;
		score += getScoreChangeFromShuffle(shuffle);
		for(int i = i2 - 1; i > i1; i--) {
			shuffle = new Shuffle(i + 1, i);
			if(!isShufflePossible(shuffle)) return false;
			shuffles.add(shuffle);
			shuffleRangeSum += shuffle.to - shuffle.from  + 1;
			score += getScoreChangeFromShuffle(shuffle);
		}

		long temp = a.get(i1);
		a.set(i1, a.get(i2));
		a.set(i2, temp);

		return true;
	}
	

	private boolean checkAndAddShuffle(Shuffle shuffle) {
		if(isShufflePossible(shuffle)) {
			addShuffle(shuffle);
			return true;
		}

		return false;
	}
	
	public Shuffle getNextShuffle() {
		int mid = (a.size() / 2);
		int suitableIndex = getSuitableIndex();
		if(suitableIndex >= 0) return new Shuffle(suitableIndex + 1, mid + mid);

		return null;
	}

	private int getSuitableIndex() {
		int mid = (a.size() / 2);
		long maxDifference = 1;
		int suitableIndex = -1;
		for(int i = mid - 1; i >= 0; i--) {
			long newScore = score + getScoreChangeFromShuffle(new Shuffle(i + 1, mid + 1));
			long newDifference = Math.abs(score) - Math.abs(newScore);
			if(newDifference > maxDifference) {
//				return suitableIndex;
				maxDifference = newDifference; 
				suitableIndex = i;
			}
		}

		return suitableIndex;
	}

	private int findIndexGreaterThan(long val, int from, int to) {
		for(int i = from; i <= to; i++)
			if(a.get(i) > val) return i;

		return -1;
	}

	private int findIndexLessThan(long val, int from, int to) {
		for(int i = from; i <= to; i++)
			if(a.get(i) < val) return i;

		return -1;
	}

	public void addShuffle(Shuffle shuffle) {
		long end = a.get(shuffle.from - 1);
		for(int i = shuffle.from - 1; i < shuffle.to - 1; i++) 
			a.set(i, a.get(i + 1));

		a.set(shuffle.to - 1, end);
		shuffles.add(shuffle);
		shuffleRangeSum += shuffle.to - shuffle.from  + 1;
		score += getScoreChangeFromShuffle(shuffle);

		for(long ai : a) System.err.print(ai + " ");
		System.err.println();
	}

	private long getScoreChangeFromShuffle(Shuffle shuffle) {
		int mid = (a.size() / 2) + 1;
		if(shuffle.from < mid && shuffle.to >= mid)
			 return 2 * (a.get(mid - 1) - a.get(shuffle.from - 1));

		return 0;
	}

	private long computeScore() {
		long s = 0;
		for(int i = 0; i < a.size(); i++) {
			if(i + i < a.size()) s += a.get(i);
			else				 s -= a.get(i);
		}
		return s;
	}

	public long getScore() {
		return Math.abs(initialScore) - Math.abs(computeScore());
	}

	public ArrayList<Shuffle> getShuffles() {
		return shuffles;
	}
}
