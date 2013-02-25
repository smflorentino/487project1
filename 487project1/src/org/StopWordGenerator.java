package org;
/**
 * Adds all stopwords to hashmap so that they can be ignored in tweet word counts
 * This class is implemented during MapReduce, see TweetOutputSorter.java
 * @author Scott
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;


public class StopWordGenerator {
	
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
