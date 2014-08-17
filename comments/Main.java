import java.util.*;
import java.io.*;

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	private static final int BUFFER_SIZE = 101 * 1024;

	private static final int NEED_ASTERISK_TO_OPEN 	= 0;
	private static final int NEED_SLASH 			= 1;
	private static final int NEED_SLASH_TO_CLOSE 	= 2;
	private static final int ANY 					= 3;
	private static final int COMMENT 				= 4;


	public static void output(char[] input, int size) throws Exception {
		int state = ANY;
		int commentLevel = 0;
		int i = 0;
		for(i = 0; i < size - 1; i++) {
			if(input[i] == '/' && input[i + 1] == '*') {
				i++;
				commentLevel++;
			}
			else if(input[i] == '*' && input[i + 1] == '/' && commentLevel > 0) {
				i++;
				commentLevel--;
			}
			else if(commentLevel == 0) {
				out.print(input[i]);
			}
		}
		if(commentLevel != 0) System.err.println("ERROR!");
		out.println();
	}

	public static void main(String args[]) throws Exception {		
		char[] input = new char[BUFFER_SIZE];
		int size = in.read(input);
		out.println("Case #1:");
		output(input, size);
		out.flush();
	}
}
