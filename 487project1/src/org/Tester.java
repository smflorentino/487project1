package org;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.TwitterCounter.TweetTokenizer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

/**Class used for testing the data analysis
 * @author Alyssa
 * @author Scott
 * 
 */

public class Tester {

	public static void main(String args[]) throws IOException{
		//TweetTokenizer t = new TweetTokenizer("<un>Test User</un><tt>Hello world1</tt><dt></dt><rt></rt><un>Test User</un><tt>Hello world2</tt><dt></dt><rt></rt><un>Test User</un><tt>Hello world3</tt><dt></dt><rt></rt>");
		
		String fileName = JOptionPane.showInputDialog("Enter name of test file");
		BufferedReader sc = new BufferedReader(new FileReader(fileName));
		String line;
		String tweet;
		TweetTokenizer t;
		t=new TweetTokenizer();

		while(true){
			line=sc.readLine();
			if(line==null) {
			break;
			}
			else {
				tweet=t.getTweet(line);
				if(line.length()>0){
					System.out.println("Tweet is: "+tweet);
					System.out.println("Words in tweet: "+t.getTweetWordCount(tweet));
					System.out.println("Mentions are: "+t.getMentions(tweet));
					System.out.println("Tags are: "+t.getTags(tweet));
				}
			}

		}
		System.out.println("TESTER FINISHED");
	
	}
	
	
	
}
