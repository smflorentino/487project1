package org;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JOptionPane;


public class FileCleaner {
	
	
	public static void main(String[] args) {
		FileCleaner f = new FileCleaner();
		try {
			f.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void start() throws IOException {
		String fileName = JOptionPane.showInputDialog("Enter file name to clean");
		BufferedReader sc = new BufferedReader(new FileReader(fileName));
		FileWriter fs=  new FileWriter(fileName+"clean");
		BufferedWriter out = new BufferedWriter(fs);
		
		String line;
		int linenum=0;
		while(true) {
			//System.out.println("Line:" + linenum);
			line=sc.readLine();
			if(line == null) {
				break;
			}
			if(this.isValidLine(line)) {
				out.write(line+'\n');
			}
			else {
			//	System.out.println("Found invalid line" + line);
			}
			linenum++;
		}
		out.close();
	}
	
	private boolean isValidLine(String s) {
		char c;
		for(int i=0;i<s.length();i++) {
			c=s.charAt(i);
			if(!(this.isValidCharacter(c))) {
				
				return false;
			}
		}
		//System.out.println("found valid line:");
		return true;
	}
	
	private boolean isValidCharacter(char c) {
		//.out.println(c+0);
		if(c >=32 && c<=126) {
			//System.out.println("Special");
			return true;
		}
		return false;
	}
}
