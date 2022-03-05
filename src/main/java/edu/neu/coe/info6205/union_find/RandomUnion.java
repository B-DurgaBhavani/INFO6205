package edu.neu.coe.info6205.union_find;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class RandomUnion {

	private static int count(int n) {
		UF_HWQUPC uf = new UF_HWQUPC(n);
		int count = 0;
		Random r = new Random();
		while(uf.getCount() > 1){
			int p = r.nextInt(n);
			int q = r.nextInt(n);
			uf.connect(p, q);
			count+=1;
		}
		return count;
	}

	public static void main(String[] args) {
		String graph = "";
		for (int i = 10; i < 100000; i++) {
			int result = count(i);
			graph = graph + i + " " + result + "\n";
			System.out.println(" " + i + " " + result);
		}
		try {
			FileWriter myWriter = new FileWriter("durga.txt");
			System.out.println("File created..");
			myWriter.write(graph);
			System.out.println("File written..");
			myWriter.close();
			System.out.println("File closed..");
		}
		catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
}