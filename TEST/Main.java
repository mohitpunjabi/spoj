import java.io.*;

public class Main {
	
	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String s;
		while(!(s = br.readLine()).equals("42")) System.out.println(s);
	}

}
