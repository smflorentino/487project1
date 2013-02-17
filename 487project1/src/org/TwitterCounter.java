package org;

import java.io.IOException;
import java.util.*;

import org.WordCount.Map;
import org.WordCount.Reduce;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

public class TwitterCounter {
	//new comment for timestamp
	public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {
		private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();

		//key is the file name - each mapper processes a file
		//value is the whole file (as text?)
		//TODO: what is OutputCollector?  Just a new instance?
		public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
			//tokenize value (whole file) into individual tweets
			//then tokenize each tweet for word count
			String data = value.toString();
			String tweet;
			TweetTokenizer tweetTokenizer = new TweetTokenizer(data);
			while (tweetTokenizer.hasMoreElements()) {
				tweet = tweetTokenizer.nextElement();
				StringTokenizer stringTokenizer = new StringTokenizer(tweet);
				while (stringTokenizer.hasMoreTokens()) {
					word.set(stringTokenizer.nextToken());
					output.collect(word, one);
				}
			}
		}
	}



	public static class Reduce extends MapReduceBase 
	implements Reducer<Text, IntWritable, Text, IntWritable> {
		public void reduce(Text key, Iterator<IntWritable> values, 
				OutputCollector<Text, IntWritable> output, Reporter reporter) 
						throws IOException {
			int sum = 0;
			while (values.hasNext()) {
				sum += values.next().get();
			}
			output.collect(key, new IntWritable(sum));

		}
	}

	public static void main(String[] args) throws Exception {
		JobConf conf = new JobConf(TwitterCounter.class);
		conf.setJobName("twittercounter");
		conf.setJarByClass(TwitterCounter.class);
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(IntWritable.class);

		conf.setMapperClass(Map.class);
		conf.setCombinerClass(Reduce.class);
		conf.setReducerClass(Reduce.class);

		conf.setInputFormat(TextInputFormat.class);
		conf.setOutputFormat(TextOutputFormat.class);

		//input/output paths given in args
		FileInputFormat.setInputPaths(conf, new Path(args[0]));
		FileOutputFormat.setOutputPath(conf, new Path(args[1]));

		JobClient.runJob(conf);
	}


	public static class TweetTokenizer implements Enumeration<Object>{
		String _value;

		public TweetTokenizer(String value){
			_value=value;
		}


		//determines if there is another opening <tt> tag i.e. another tweet text in the file
		@Override
		public boolean hasMoreElements() {
			for(int i=0; i<_value.length()+4;i++){
				if(_value.charAt(i)=='<'&&_value.charAt(i+1)=='t'&&_value.charAt(i+2)=='t'
						&&_value.charAt(i+3)=='>'){
					//found <tt> tag
					return true;
				}
			}
			return false;
		}

		//gets the next tweet i.e. everything between <tt> </tt> tags
		@Override
		public String nextElement() {
			String s="";
			for(int i=0; i<_value.length()+4;i++){
				if(_value.charAt(i)=='<'&&_value.charAt(i+1)=='t'&&_value.charAt(i+2)=='t'
						&&_value.charAt(i+3)=='>'){
					//found opening tag <tt>
					for(int j=i+4;j<_value.length()+5;j++){
						if(_value.charAt(j)=='<'&&_value.charAt(j+1)=='/'&&
								_value.charAt(j+2)=='t'&&_value.charAt(j+3)=='t'&&
								_value.charAt(j+4)=='>'){
							//found closing tag </tt>
							return s;
						}else{
							s=s+_value.charAt(j);
						}
					}
				}
			}
			return "";
		}

	}
}