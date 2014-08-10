import java.util.*;
import java.io.*;

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	private static void rotate(ArrayList<Long> a, int from, int to) {
		long end = a.get(from);
		for(int i = from; i < to; i++) a.set(i, a.get(i + 1));
		a.set(to, end);
	}

	public static long f(ArrayList<Long> a) {
		long s = 0;
		for(int i = 0; i < a.size(); i++) {
			if(i + i < a.size()) s += a.get(i);
			else				 s -= a.get(i);
		}

		return Math.abs(s);
	}

	public static double score(ArrayList<Long> a, ArrayList<Shuffle> shuffles) throws Exception {
		long init = f(a);

		int rangeSum = 0;
		for(int i = 0; i < shuffles.size(); i++) 
			rotate(a, shuffles.get(i).from - 1, shuffles.get(i).to - 1);

		long s = f(a);
		return (double) (init - s) / init;
	}

	public static void output(ArrayList<Long> a) throws Exception {
		NewShuffler newShuffler = new NewShuffler(new ArrayList<Long>(a));
		OldShuffler oldShuffler = new OldShuffler(new ArrayList<Long>(a));
		newShuffler.minimizeScore();
		oldShuffler.minimizeScore();

		ArrayList<Shuffle> shuffles = null;
		if(score(new ArrayList<Long>(a), newShuffler.getShuffles()) >
		   score(new ArrayList<Long>(a), newShuffler.getShuffles())) {
			shuffles = newShuffler.getShuffles();
		}
		else
			shuffles = oldShuffler.getShuffles();

		out.println(shuffles.size());
		for(Shuffle shuffle : shuffles) out.println(shuffle.toString());
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

abstract class Shuffler { 
	protected ArrayList<Long> a;
	protected ArrayList<Shuffle> shuffles;
	protected int shuffleRangeSum;
	protected long score;
	protected long initialScore;
	protected Random rand;

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

	public abstract void minimizeScore();

	protected boolean checkAndAddShuffle(Shuffle shuffle) {
		if(isShufflePossible(shuffle)) {
			addShuffle(shuffle);
			return true;
		}

		return false;
	}
	
	protected abstract Shuffle getNextShuffle();

	protected void addShuffle(Shuffle shuffle) {
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

	protected long getScoreChangeFromShuffle(Shuffle shuffle) {
		int mid = (a.size() / 2) + 1;
		if(shuffle.from < mid && shuffle.to >= mid)
			 return 2 * (a.get(mid - 1) - a.get(shuffle.from - 1));

		return 0;
	}

	protected long computeScore() {
		long s = 0;
		for(int i = 0; i < a.size(); i++) {
			if(i + i < a.size()) s += a.get(i);
			else				 s -= a.get(i);
		}
		return s;
	}

	public long getScore() {
		return Math.abs(initialScore) - Math.abs(score);
	}

	public ArrayList<Shuffle> getShuffles() {
		return shuffles;
	}
}

class NewShuffler extends Shuffler { 

	public NewShuffler(ArrayList<Long> a) {
		super(a);
	}

	public void minimizeScore() {
		Shuffle shuffle = getNextShuffle();
		int mid = (a.size() / 2);
		int midIndexNotChecked = mid;
		while(true) {
			checkAndAddShuffle(shuffle);
			if(!trySwap(mid, ++midIndexNotChecked)) return;
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
	
	protected Shuffle getNextShuffle() {
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
			long newScore = score + getScoreChangeFromShuffle(new Shuffle(i + 1, mid + mid));
			long newDifference = Math.abs(score) - Math.abs(newScore);
			if(newDifference > maxDifference) {
				maxDifference = newDifference; 
				suitableIndex = i;
			}
		}

		return suitableIndex;
	}
}

class OldShuffler extends Shuffler { 

	public OldShuffler(ArrayList<Long> a) {
		super(a);
	}

	public void minimizeScore() {
		Shuffle shuffle = getNextShuffle();
		while(true) {
			if(isShufflePossible(shuffle))	addShuffle(shuffle);
			else {
				int mid = (a.size() / 2);
				Shuffle newMid = new Shuffle(mid + 1, mid + mid);
				if(isShufflePossible(newMid))	addShuffle(newMid);
				else							break;
			}

			shuffle = getNextShuffle();
		}
	}

	private int getSuitableIndex() {
		int mid = (a.size() / 2);
		long maxDifference = 0;
		int suitableIndex = -1;
		for(int i = 0; i < mid; i++) {
			long newScore = score + getScoreChangeFromShuffle(new Shuffle(i + 1, mid + mid));
			long newDifference = Math.abs(score) - Math.abs(newScore);
			if(newDifference > maxDifference) {
				maxDifference = newDifference; 
				suitableIndex = i;
			}
		}

		return suitableIndex;
	}

	protected Shuffle getNextShuffle() {
		int mid = (a.size() / 2);
		int suitableIndex = getSuitableIndex();
		if(suitableIndex >= 0) return new Shuffle(suitableIndex + 1, mid + mid);

		return null;
	}

}
