package org;

import org.TwitterCounter.TweetTokenizer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class Tester {
//this shows that our TweetTokenizer class appears to be working
	public static void main(String args[]){
		TweetTokenizer t = new TweetTokenizer("<un>Test User</un><tt>Hello world1</tt><dt></dt><rt></rt><un>Test User</un><tt>Hello world2</tt><dt></dt><rt></rt><un>Test User</un><tt>Hello world3</tt><dt></dt><rt></rt>");
		while(t.hasMoreElements()){
			System.out.println("hasMoreElements true");
			System.out.println("Current token: "+t.nextElement());
		}
	
	}
	
	
	
}
