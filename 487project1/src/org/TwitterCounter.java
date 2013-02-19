package org;

import java.io.IOException;
import java.util.*;

import org.TwitterCounter.Map;
import org.TwitterCounter.Reduce;
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
		//value is the whole file (as Text)
		public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
			//tokenize value (whole file) into individual tweets
			//then tokenize each tweet for word count
			String data = value.toString();
			String lines[] = data.split("[\\r\\n]+");
			String tweet;
			StringTokenizer stringTokenizer;
			TweetTokenizer tweetTokenizer = new TweetTokenizer();
			//while (tweetTokenizer.hasMoreElements()) {
			for(int i=0; i<lines.length;i++){
				tweet = tweetTokenizer.getTweet(lines[i]);
				if(tweet.length()>0){
					stringTokenizer = new StringTokenizer(tweet);
					while (stringTokenizer.hasMoreTokens()) {
						word.set(stringTokenizer.nextToken());
						output.collect(word, one);
					}
				}
			}
				

			//}
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


	public static class TweetTokenizer {
		public String getTweet(String line){
			for(int i=0; i<line.length()-4;i++){
	//			System.out.print(line.charAt(i));
				if(line.charAt(i)=='<'&&line.charAt(i+1)=='t'&&line.charAt(i+2)=='t'
						&&line.charAt(i+3)=='>'){
					//found opening tag <tt>
					for(int j=i+4;j<line.length()-5;j++){
						if(line.charAt(j)=='<'&&line.charAt(j+1)=='/'&&
								line.charAt(j+2)=='t'&&line.charAt(j+3)=='t'&&
								line.charAt(j+4)=='>'){
							//found closing tag </tt>
							return line.substring(i+4,j);
						}
					}
				}
			}
			return "";
		}
		
		public ArrayList<String> getMentions(String tweetText){
			ArrayList<String> mentions = new ArrayList<String>();
			for(int i=0; i<tweetText.length();i++){
				if(tweetText.charAt(i)==64){
					//get the rest of the mention
					for(int j=i+1;j<tweetText.length();j++){
						if(tweetText.charAt(j)==32|| j==tweetText.length()-1){
							mentions.add(tweetText.substring(i,j));
							j=tweetText.length();
							i=j;
						}
					}
				}
			}
			return mentions;
		}
//		String _value;
//		int _currentIndex;
//
//		public TweetTokenizer(String value){
//			_value=value;
//			_currentIndex=0;
//		}
//
//
//		//determines if there is another opening <tt> tag i.e. another tweet text in the file
//		@Override
//		public boolean hasMoreElements() {
//			System.out.print("Looking for more elements starting at index: "+_currentIndex);
//			for(int i=_currentIndex; i<_value.length()-4;i++){
//				if(_value.charAt(i)=='<'&&_value.charAt(i+1)=='t'&&_value.charAt(i+2)=='t'
//						&&_value.charAt(i+3)=='>'){
//					//found <tt> tag
//					System.out.println("Index of next tweet start: "+i+4);
//					return true;
//				}
//			}
//			return false;
//		}
//
//		//gets the next tweet i.e. everything between <tt> </tt> tags
//		@Override
//		public String nextElement() {
//			for(int i=_currentIndex; i<_value.length()-4;i++){
//				System.out.print(_value.charAt(i));
//				if(_value.charAt(i)=='<'&&_value.charAt(i+1)=='t'&&_value.charAt(i+2)=='t'
//						&&_value.charAt(i+3)=='>'){
//					//found opening tag <tt>
//					for(int j=i+4;j<_value.length()-5;j++){
//						if(_value.charAt(j)=='<'&&_value.charAt(j+1)=='/'&&
//								_value.charAt(j+2)=='t'&&_value.charAt(j+3)=='t'&&
//								_value.charAt(j+4)=='>'){
//							//found closing tag </tt>
//							_currentIndex=j+5;
//							return _value.substring(i+4,j);
//						}
//					}
//				}
//			}
//			return "";
//		}

	}



}