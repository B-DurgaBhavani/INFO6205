package edu.neu.coe.info6205.union_find;

import java.util.*;
import java.io.*;

public class RandomUnion {
	
	public static void main(String[] args) {
		String graph = "";
		
		for (int i = 10; i<100000; i++) {
			int N=i;
			boolean pathCompression = true;
			UF_HWQUPC randomUF = new UF_HWQUPC(N,pathCompression);
			Random r = new Random();
			int result =0;
			
			while(randomUF.components()>1) {
				result++;
				int p = r.nextInt(N);
				int q = r.nextInt(N);
				if(randomUF.connected(p, q)) {
					continue;
				}
				randomUF.connect(p, q);
			}
			graph =graph+""+ N+" "+result+"\n";
			System.out.println(" "+N+" "+result);
		}
		try {
//			System.out.println("Graph printing: \n"+graph);
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
