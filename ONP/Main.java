import java.util.*;
import java.io.*;

public class Main {

	private static final char[] operators = {'(', '+', '-', '*', '/', '^'};

	private static boolean isOperand(char c) {
		return ((int) c >= (int) 'a') && ((int) c <= (int) 'z');
	}

	private static boolean isOperator(char c) {
		for(int i = 0; i < operators.length; i++)
			if(operators[i] == c) return true;
		return false;
	}

	private static boolean hasHigherPriority(char op1, char op2) {
		for(int i = 0; i < operators.length; i++) {
			if(operators[i] == op1) return false;
			if(operators[i] == op2) return true;
		}

		return false; 
	}

	public static String getPostfix(String infix) {
		Stack<Character> stack = new Stack<Character>();
		String postfix = "";

		for(int i = 0; i < infix.length(); i++) {
			char curr = infix.charAt(i);

			if(isOperand(curr))  postfix += curr;
			else if(curr == '(') stack.push(curr);
			else if(isOperator(curr)) {
				while(!stack.empty() && hasHigherPriority(stack.peek(), curr))
					postfix += stack.pop();

				stack.push(curr);
			}
			else if(curr == ')') {
				while(!stack.empty() && stack.peek() != '(')
					postfix += stack.pop();
				if(!stack.empty()) stack.pop();
			}
		}

		while(!stack.empty())
			if(stack.peek() != '(') postfix += stack.pop();

		return postfix;
	}

	public static void output(String infix) {
		System.out.print(getPostfix(infix));
	}

	public static void main(String args[]) throws Exception {		
		Scanner scanner = new Scanner(System.in);

		int testCases = Integer.parseInt(scanner.nextLine());
		for(int i = 0; i < testCases; i++) {
			if(i > 0) System.out.println();

			String infix = scanner.nextLine();
			output(infix);
		}
	}

}
