import java.util.*;
import java.io.*;

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public static void output(Party party) throws Exception {
		out.print(party.getWaysToParty());
	}

	public static void main(String args[]) throws Exception {		
		int testCases = Integer.parseInt(in.readLine());
		for(int i = 0; i < testCases; i++) {
			StringTokenizer tokenizer = new StringTokenizer(in.readLine());
			int n = Integer.parseInt(tokenizer.nextToken());

			Party party = new Party();
			for(int j = 0; j < n; j++) {
				Person person = new Person(j);
				tokenizer = new StringTokenizer(in.readLine());
				while(tokenizer.hasMoreTokens())
					person.add(Integer.parseInt(tokenizer.nextToken()));

				party.invite(person);
			}

			output(party);
			out.println();
		}

		out.flush();
	}
}

class ModMath {
	private static final long MOD = ((long) 1e9) + 9;

	public static long add(Long ... nums) {
		long sum = 0;
		for(long num : nums) sum = (sum + num) % MOD;
		return (sum + MOD) % MOD;
	}

	public static long add(ArrayList<Integer> nums) {
		return add((Long[]) nums.toArray());
	}

	public static long mul(Long ... nums) {
		long product = 1;
		for(long num : nums) product = (product * (num % MOD)) % MOD;
		return product;
	}
}

class Party {
	private long[] ways;
	private long[] waysBuffer;
	private Person lastInvited;
	private int peopleInvited;
	private long waysToParty;

	public Party() {
		ways = new long[Person.TSHIRT_LIMIT];
		waysBuffer = new long[Person.TSHIRT_LIMIT];
		peopleInvited = 0;
		lastInvited = null;

		for(int i = 0; i < Person.TSHIRT_LIMIT; i++) {
			ways[i] = 0;
			waysBuffer[i] = 0;
		}
		waysToParty = 1;
	}

	public void invite(Person person) {
		if(peopleInvited == 0)
			for(int tshirt : person.getAllTshirts()) waysBuffer[tshirt] = 1;
		else {
			for(int tshirt = 1; tshirt < Person.TSHIRT_LIMIT; tshirt++) {
				// waysToPartyWithT = ways[tshirt];
				// waysToPartyWithout = waysToParty - waysToPartyWithT;
				// ways[tshirt] = waysWithoutT * 1 + waysWithT * (totalTshirts - 1);

				waysBuffer[tshirt] = ModMath.add(waysToParty, ModMath.mul((long) (person.getTshirtCount() - 2),  ways[tshirt]));
			}
		}

		peopleInvited++;
		waysToParty = getNewWaysToParty(person, waysToParty);
		for(int i = 0; i < ways.length; i++) ways[i] = waysBuffer[i];
/*
		System.err.println("Invited " + person.toString());
		for(int i = 0; i < 20; i++) System.err.print(ways[i] + " ");
		System.err.println("| " + waysToParty);
*/
	}

	public long getWaysToParty() {
		return waysToParty;
	}

	private long getNewWaysToParty(Person person, long oldWaysToParty) {
		long newWaysToParty = 0;
		for(int tshirt : person.getAllTshirts())
			newWaysToParty = ModMath.add(newWaysToParty, oldWaysToParty, -ways[tshirt]);
		return newWaysToParty;
	}
}

class Person {
	public static final int TSHIRT_LIMIT = 101;
	public static final int PERSON_LIMIT = 11;

	private boolean[] tshirts;
	private ArrayList<Integer> allTshirts;
	private int id;

	public Person(int id) {
		this.id = id;
		tshirts = new boolean[TSHIRT_LIMIT];
		allTshirts = new ArrayList<Integer>();
		for(int i = 0; i < TSHIRT_LIMIT; tshirts[i++] = false);
	}

	public boolean has(int tshirt) {
		return tshirts[tshirt];
	}

	public void add(int tshirt) {
		tshirts[tshirt] = true;
		allTshirts.add(tshirt);
	}

	public ArrayList<Integer> getAllTshirts() {
		return allTshirts;
	}

	public int getTshirtCount() {
		return allTshirts.size();
	}

	public String toString() {
		String string = "Person " + id + ": ";
		for(int tshirt : allTshirts) string += tshirt + " ";
		return string + "[" + getTshirtCount() + "]";
	}
}
