import java.util.*;
import java.io.*;

class DirectoryManager {
	private static Directory root;
	private static int dirCount;

	static {
		root = new Directory("", null);
		dirCount = 0;
	}

	public static int getDirCount() {
		return dirCount;
	}
	

	public static int getNewDirectoryCount(String path) {
		StringTokenizer tokenizer = new StringTokenizer(path, "/");

		Directory current = root;
		int newDirCount = 0;
		while(tokenizer.hasMoreTokens()) {
			String nextDir = tokenizer.nextToken();
			if(current != null && current.has(nextDir)) current = current.get(nextDir);
			else {
				current = null;
				newDirCount++;
			}
		}

		return newDirCount;
	}

	public static void mkdir(String path) {
		StringTokenizer tokenizer = new StringTokenizer(path, "/");

		Directory current = root;
		while(tokenizer.hasMoreTokens()) {
			String nextDir = tokenizer.nextToken();
			if(current.has(nextDir)) current = current.get(nextDir);
			else {
				dirCount++;
				Directory newDirectory = new Directory(nextDir, current);
				current = newDirectory;
			}
		}
	}

}

class Directory {
	private String name;
	private HashMap<String, Directory> children;
	private Directory parent;

	public Directory(String name, Directory parent) {
		this.name = name;
		children = new HashMap<String, Directory>();
		this.parent = parent;
		if(parent != null) parent.add(this);
	}

	public void add(Directory d) {
		children.put(d.getName(), d);
	}

	public String getName() {
		return name;
	}

	public Directory get(String dir) {
		return children.get(dir);
	}

	public boolean has(String dir) {
		return children.containsKey(dir);
	}

	public String toString() {
		if(parent == null) return "";
		return parent.toString() + "/" + name;
	}
}

public class Main {
	private static final PrintStream out = new PrintStream(new BufferedOutputStream(System.out));
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	public static void output(int existingDirs) throws Exception {
		out.print(DirectoryManager.getDirCount() - existingDirs);
	}

	public static void main(String args[]) throws Exception {		
		int testCases = Integer.parseInt(in.readLine());
		for(int i = 0; i < testCases; i++) {
			StringTokenizer tokenizer = new StringTokenizer(in.readLine());
			int n = Integer.parseInt(tokenizer.nextToken()),
				m = Integer.parseInt(tokenizer.nextToken());

			for(int j = 0; j < n; j++) DirectoryManager.mkdir(in.readLine());
			int existingDirs = DirectoryManager.getDirCount();
			for(int j = 0; j < m; j++) DirectoryManager.mkdir(in.readLine());

			out.print("Case #" + (i + 1) + ": ");
			output(existingDirs);
			out.println();
			out.flush();
		}

	}
}
