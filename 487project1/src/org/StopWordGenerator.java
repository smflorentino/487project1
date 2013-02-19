package org;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

public class StopWordGenerator {
	public static void main(String[] args) throws IOException {
	String fileName = JOptionPane.showInputDialog("Enter filename with stop words:");
	BufferedReader sc = new BufferedReader(new FileReader(fileName));
	FileWriter fs=  new FileWriter(fileName+"code.txt");
	BufferedWriter out = new BufferedWriter(fs);
	
	String line;
	String[] words;
	while(true) {
		//System.out.println("Line:" + linenum);
		line=sc.readLine();
		
		if(line == null) {
			break;
		}
		words = line.split(",");
			for(int i =0;i<words.length;i++) {
				out.write("STOPWORDS.put(\"" + words[i] + "\",0" + ");\r\n");
			}
	}
	out.close();
	}
}
