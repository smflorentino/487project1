package org;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import javax.swing.JOptionPane;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;
//a MR job to take the MapReduce Output and sort it in ascending order by count and remove stop words

public class TweetOutputSorter {
 
  public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, IntWritable, Text> {
    private final static IntWritable one = new IntWritable(1);
    private IntWritable count = new IntWritable();
    private Text word = new Text();
    
    public void map(LongWritable key, Text value, OutputCollector<IntWritable, Text> output, Reporter reporter) throws IOException {
      String line = value.toString();
      StringTokenizer tokenizer = new StringTokenizer(line);
      
      //output.collect(count, word);
      while (tokenizer.hasMoreTokens()) {
    	word.set(tokenizer.nextToken());
        count.set(Integer.parseInt(tokenizer.nextToken()));
        output.collect(count, word);
          //word.set(tokenizer.nextToken());
      }
    }
  }
 
  public static class Reduce extends MapReduceBase implements Reducer<IntWritable, Text, IntWritable, Text> {
	  private static StopWords sw = new StopWords();
	  public void reduce(IntWritable key, Iterator<Text> values, OutputCollector<IntWritable, Text> output, Reporter reporter) throws IOException {
      while (values.hasNext()) {
    	  Text current = values.next();
    	  String word = current.toString();
    	if(sw.STOPWORDS.get(word)==null) {
    		output.collect(key,current);
    	}
      }
    }
  }
 
  public static void main(String[] args) throws Exception {
	//StopWords.initializeHashMap();
    JobConf conf = new JobConf(TweetOutputSorter.class);
    conf.setJobName("tweetoutputsorter");
 
    conf.setOutputKeyClass(IntWritable.class);
    conf.setOutputValueClass(Text.class);
 
    conf.setMapperClass(Map.class);
    conf.setCombinerClass(Reduce.class);
    conf.setReducerClass(Reduce.class);
    conf.setOutputKeyComparatorClass(CountCompare.class);
    conf.setInputFormat(TextInputFormat.class);
    conf.setOutputFormat(TextOutputFormat.class);
 
    FileInputFormat.setInputPaths(conf, new Path(args[0]));
    FileOutputFormat.setOutputPath(conf, new Path(args[1]));
 
    JobClient.runJob(conf);
  }
  
  public static class CountCompare extends IntWritable.Comparator {

	@Override
	public int compare(byte[] arg0, int arg1, int arg2, byte[] arg3, int arg4,
			int arg5) {
		return -super.compare(arg0,arg1,arg2,arg3,arg4,arg5);
	}
  }

  //reads in stop words (comma separated) from config/stopwords.txt
  public static class StopWords {
	  public static HashMap<String, Integer> STOPWORDS;

	  public StopWords() {
		  BufferedReader sc = null;
		try {
			sc = new BufferedReader(new FileReader("config/stopwords.txt"));
		} catch (FileNotFoundException e1) {
			System.err.println("The config/stopwords.txt was not found");
			e1.printStackTrace();
		}

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
				  STOPWORDS.put(words[i],0);
			  }
		  }
	  }
  }
	

	
	  
  }

