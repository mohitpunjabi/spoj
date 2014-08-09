import java.util.*;
import java.io.*;

class Party {
	private ArrayList<Person> partyPeople;
	private ArrayList<HashMap<String, Long>> cache;

	class PersonComparator implements Comparator<Person> {
		public int compare(Person p1, Person p2) {
			if(p1.getTshirtCount() < p2.getTshirtCount()) return -1;
			if(p1.getTshirtCount() > p2.getTshirtCount()) return 1;

			return 0;
		}
	}

	public Party() {
		partyPeople = new ArrayList<Person>();
		cache = new ArrayList<HashMap<String, Long>>();
	}

	public void invite(Person person) {
		partyPeople.add(person);
	}

	public long getWaysToParty() {
		for(int i = 0; i < partyPeople.size(); i++)	cache.add(new HashMap<String, Long>());
		Collections.sort(partyPeople, new PersonComparator());
		boolean[] tshirtUsed = new boolean[TshirtFactory.TSHIRT_LIMIT];
		for(int i = 0; i < tshirtUsed.length; tshirtUsed[i++] = false);
		return getWaysToPartyHelper(0, tshirtUsed);
	}

	private long getWaysToPartyHelper(int fromId, boolean[] tshirtUsed) {
		if(fromId == partyPeople.size()) return 1;
	
		String personKey = generateKey(fromId, tshirtUsed);
		long ways = getWaysFromCache(fromId, personKey);
		
		if(ways >= 0) return ways;
		ways = 0;
		long uniqueTshirts = 0;
		for(int tshirt : partyPeople.get(fromId).getAllTshirts()) {
			if(TshirtFactory.getCountOf(tshirt) == 1) 
				uniqueTshirts++;
			else if(!tshirtUsed[tshirt]) {
				tshirtUsed[tshirt] = true;
				ways = ModMath.add(ways, getWaysToPartyHelper(fromId + 1, tshirtUsed));
				tshirtUsed[tshirt] = false;
			}
		}
		if(uniqueTshirts >= 1)
			ways = ModMath.add(ways, ModMath.mul(uniqueTshirts, getWaysToPartyHelper(fromId + 1, tshirtUsed)));

		cache.get(fromId).put(personKey, ways);
		return ways;
	}

	private String generateKey(int fromId, boolean[] tshirtUsed) {
		StringBuffer key = new StringBuffer();
		for(int i = 0; i < tshirtUsed.length; i++) {
			for(int j = fromId; j < partyPeople.size(); j++) {
				Person person = partyPeople.get(j);
				if(person.has(i)) {
					if(tshirtUsed[i]) key.append("1");
					else			  key.append("0");
					break;
				}
			}
		}

		return key.toString();
	}

	private long getWaysFromCache(int fromId, String key) {
		if(!cache.get(fromId).containsKey(key)) return -1;
		return cache.get(fromId).get(key);
	}
}

public class Solution {
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


class TshirtFactory {
	public static final int TSHIRT_LIMIT = 101;
	private static int[] tshirtCount;

	static {
		tshirtCount = new int[TSHIRT_LIMIT];
		for(int i = 0; i < TSHIRT_LIMIT; tshirtCount[i++] = 0);
	}
	
	public static int newTshirt(int type) {
		tshirtCount[type]++;
		return type;
	}

	public static int getCountOf(int tshirt) {
		return tshirtCount[tshirt];
	}
}

class Person {
	public static final int PERSON_LIMIT = 11;

	private boolean[] tshirts;
	private ArrayList<Integer> allTshirts;
	private static final TshirtComparator tshirtComparator;
	private int id;

	static {
		tshirtComparator = new TshirtComparator();
	}

	static class TshirtComparator implements Comparator<Integer> {
		public int compare(Integer t1, Integer t2) {
			if(TshirtFactory.getCountOf(t1) < TshirtFactory.getCountOf(t2)) return -1;
			if(TshirtFactory.getCountOf(t1) > TshirtFactory.getCountOf(t2)) return 1;
			return 0;
		}
	}

	public Person(int id) {
		this.id = id;
		tshirts = new boolean[TshirtFactory.TSHIRT_LIMIT];
		allTshirts = new ArrayList<Integer>();
		for(int i = 0; i < TshirtFactory.TSHIRT_LIMIT; tshirts[i++] = false);
	}

	public boolean has(int tshirt) {
		return tshirts[tshirt];
	}

	public void add(int tshirt) {
		if(!has(tshirt)) {
			tshirts[tshirt] = true;
			allTshirts.add(TshirtFactory.newTshirt(tshirt));
		}
	}

	public ArrayList<Integer> getAllTshirts() {
		Collections.sort(allTshirts, tshirtComparator);
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

class ModMath {
	private static final long MOD = ((long) 1e9) + 9;

	public static long add(Long ... nums) {
		long sum = 0;
		for(long num : nums) sum = (sum + num) % MOD;
		return (sum + MOD) % MOD;
	}

	public static long mul(Long ... nums) {
		long product = 1;
		for(long num : nums) product = (product * (num % MOD)) % MOD;
		return product;
	}
}

