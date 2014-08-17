import java.util.*;
import java.io.*;


class Number {

	private static final String[] multiplier = {"", "", "double", "triple", "quadruple", "quintuple", "sextuple", "septuple", "octuple", "nonuple", "decuple"};
	private static final String[] word = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
	
	private char[] number;

	public Number(String num) {
		number = num.toCharArray();
		System.err.print("Number " + num + ": ");
	}

	private String digitToWord(char digit, int count) {
		StringBuffer dword = new StringBuffer();
		if(count < 2 || count > 10)
			for(int i = 0; i < count; i++) dword.append(word[digit - '0']).append(' ');
		else 
			dword.append(multiplier[count]).append(' ').append(word[digit - '0']).append(' ');
		
		return dword.toString();
	}

	public String toString() {
		StringBuffer num = new StringBuffer();
		int i = 0;
		while(i < number.length) {
			int count = 1;
			for(int j = i + 1; j < number.length && number[j] == number[i]; j++) count++;
			num.append(digitToWord(number[i], count));
			i = i + count;
		}
		System.err.println(num.toString());
		return num.toString();
	}

}

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public static void output(String phoneNumber, String format) throws Exception {
		StringTokenizer tokenizer = new StringTokenizer(format, "-");
		while(tokenizer.hasMoreTokens()) {
			int digits = Integer.parseInt(tokenizer.nextToken());
			String number = phoneNumber.substring(0, digits);
			out.print((new Number(number)).toString());
			phoneNumber = phoneNumber.substring(digits);
		}
	}

	public static void main(String args[]) throws Exception {		
		int testCases = Integer.parseInt(in.readLine());
		for(int i = 0; i < testCases; i++) {
			StringTokenizer tokenizer = new StringTokenizer(in.readLine());
			String 	phoneNumber = tokenizer.nextToken(),
					format 		= tokenizer.nextToken();

			out.print("Case #" + (i + 1) + ": ");
			output(phoneNumber, format);
			out.println();
		}

		out.flush();
	}
}
