package org;

import java.io.IOException;
import java.util.*;
 
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;
//an MR job to take the MapReduce Output and sort it in ascending order and remove stop words

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
    public void reduce(IntWritable key, Iterator<Text> values, OutputCollector<IntWritable, Text> output, Reporter reporter) throws IOException {
      //int sum = 0;
      while (values.hasNext()) {
        //sum += values.next().get();
        output.collect(key,values.next());
      }
      //output.collect(key, new IntWritable(sum));
    }
  }
 
  public static void main(String[] args) throws Exception {
    JobConf conf = new JobConf(TweetOutputSorter.class);
    conf.setJobName("tweetoutputsorter");
 
    conf.setOutputKeyClass(Text.class);
    conf.setOutputValueClass(IntWritable.class);
 
    conf.setMapperClass(Map.class);
    conf.setCombinerClass(Reduce.class);
    conf.setReducerClass(Reduce.class);
 
    conf.setInputFormat(TextInputFormat.class);
    conf.setOutputFormat(TextOutputFormat.class);
 
    FileInputFormat.setInputPaths(conf, new Path(args[0]));
    FileOutputFormat.setOutputPath(conf, new Path(args[1]));
 
    JobClient.runJob(conf);
  }
}
