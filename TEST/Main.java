import java.util.*;
import java.io.*;

public class Main {

	public static void main(String args[]) throws Exception {		
		Scanner scanner = new Scanner(System.in);
		String answer;

		while(!(answer = scanner.nextLine()).equals("42")) System.out.println(answer);
	}

}
