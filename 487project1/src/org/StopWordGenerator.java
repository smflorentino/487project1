package org;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import javax.swing.JOptionPane;
//generate Java Source Code to create a HashMap of Stop Wordss
public class StopWordGenerator {
	/*public static void main(String[] args) throws IOException {
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
	}*/
	
	public static void main(String[] args) {
		StopWords sw= new StopWords();
		System.out.println("Success");
	}
	 public static class StopWords {
		  public static HashMap<String, Integer> STOPWORDS;

		  public StopWords() {
			  STOPWORDS=new HashMap<String,Integer>();
			  BufferedReader sc = null;
			sc = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("config/stopwords.txt")));

			  String line="";
			  String[] words;
			  while(true) {
				  //System.out.println("Line:" + linenum);
				  try {
					  line=sc.readLine();
				  } catch (IOException e) {
					  System.err.println("An error occurred while reading the stop words file");
					  e.printStackTrace();
				  }

				  if(line == null) {
					  break;
				  } 
				  words = line.split(",");
				  for(int i =0;i<words.length;i++) {
					  System.out.println(words[i]);
					  STOPWORDS.put(words[i],0);
				  }
			  }
		  }
	  }
}
