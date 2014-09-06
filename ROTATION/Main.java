import java.util.*;
import java.io.*;


class RotatableArray {

	private ArrayList<Integer> elems;
	private int rotation;

	public RotatableArray(ArrayList<Integer> elems) {
		this.elems = elems;
		rotation = 0;
	}

	public void rotate(int index) {
		rotation += index + elems.size();
		rotation %= elems.size();
	}

	public Integer get(int index) {
		return elems.get((index - rotation + elems.size()) % elems.size());
	}

}

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public static void output(RotatableArray nums, int index) throws Exception {
		out.print(nums.get(index - 1));
	}

	public static void main(String args[]) throws Exception {		
		StringTokenizer tokenizer = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(tokenizer.nextToken()),
			m = Integer.parseInt(tokenizer.nextToken());

		ArrayList<Integer> nums = new ArrayList<Integer>(n);
		tokenizer = new StringTokenizer(in.readLine());
		for(int i = 0; i < n; i++) 
			nums.add(Integer.parseInt(tokenizer.nextToken()));

		RotatableArray elems = new RotatableArray(nums);
		for(int i = 0; i < m; i++) {
			tokenizer = new StringTokenizer(in.readLine());
			String type = tokenizer.nextToken();
			int index = Integer.parseInt(tokenizer.nextToken());

			if(type.equals("C"))		elems.rotate(-index);
			else if(type.equals("A"))	elems.rotate(index);
			else {
				output(elems, index);
				out.println();
			}
		}

		out.flush();
	}
}
