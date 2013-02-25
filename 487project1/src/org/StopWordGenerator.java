package org;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

public class StopWordGenerator {
	
	public static void main(String[] args) {
		String path = JOptionPane.showInputDialog("Enter a path to a file to tokenize for stop words:");
		ArrayList<String> words = new ArrayList<String>();
		BufferedReader r=null;
		try {
			r = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringTokenizer tokenizer;
		
		String line="";
		try {
			line = r.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(line!=null) {
			tokenizer = new StringTokenizer(line);
			while(tokenizer.hasMoreTokens()) {
				words.add(tokenizer.nextToken());
			}
			for(String s : words) {
				//System.out.println("Printing stop words to console...");
				System.out.print(s+",");
			}
			words= new ArrayList<String>();
			try {
				line = r.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		
		
		
		
	}
	
}