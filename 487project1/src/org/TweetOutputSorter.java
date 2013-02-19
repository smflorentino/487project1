package org;

import java.io.IOException;
import java.util.*;

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
	
	public static class StopWords {
		public static HashMap<String, Integer> STOPWORDS;
		
		public StopWords() {
			STOPWORDS = new HashMap<String, Integer>();
			STOPWORDS.put("i",0);
			STOPWORDS.put("de",0);
			STOPWORDS.put("que",0);
			STOPWORDS.put("...",0);
			STOPWORDS.put("i'm",0);
			STOPWORDS.put("la",0);
			STOPWORDS.put("-",0);
			STOPWORDS.put("u",0);
			STOPWORDS.put("el",0);
			STOPWORDS.put("en",0);
			STOPWORDS.put("!",0);
			STOPWORDS.put("?",0);
			STOPWORDS.put(".",0);
			STOPWORDS.put("di",0);
			STOPWORDS.put("A",0);
			STOPWORDS.put("te",0);
			STOPWORDS.put("ya",0);
			STOPWORDS.put("yg",0);
			STOPWORDS.put("go",0);
			STOPWORDS.put("lo",0);
			STOPWORDS.put("se",0);
			STOPWORDS.put("es",0);
			STOPWORDS.put("un",0);
			STOPWORDS.put("aku",0);
			STOPWORDS.put("see",0);
			STOPWORDS.put("it's",0);
			STOPWORDS.put("2",0);
			STOPWORDS.put("now",0);
			STOPWORDS.put("If",0);
			STOPWORDS.put("mi",0);
			STOPWORDS.put("con",0);
			STOPWORDS.put("My",0);
			STOPWORDS.put("going",0);
			STOPWORDS.put("si",0);
			STOPWORDS.put("No",0);
			STOPWORDS.put("o",0);
			STOPWORDS.put("tu",0);
			STOPWORDS.put("back",0);
			STOPWORDS.put("ada",0);
			STOPWORDS.put("dia",0);
			STOPWORDS.put("dan",0);
			STOPWORDS.put("para",0);
			STOPWORDS.put("This",0);
			STOPWORDS.put("itu",0);
			STOPWORDS.put(";)",0);
			STOPWORDS.put("los",0);
			STOPWORDS.put("It's",0);
			STOPWORDS.put("aja",0);
			STOPWORDS.put("..",0);
			STOPWORDS.put("come",0);
			STOPWORDS.put("So",0);
			STOPWORDS.put("ga",0);
			STOPWORDS.put("una",0);
			STOPWORDS.put("im",0);
			STOPWORDS.put("ini",0);
			STOPWORDS.put("je",0);
			STOPWORDS.put("las",0);
			STOPWORDS.put("x",0);
			STOPWORDS.put("eu",0);
			STOPWORDS.put("ke",0);
			STOPWORDS.put("hahaha",0);
			STOPWORDS.put("We",0);
			STOPWORDS.put("mas",0);
			STOPWORDS.put("that's",0);
			STOPWORDS.put("tak",0);
			STOPWORDS.put("mau",0);
			STOPWORDS.put("kamu",0);
			STOPWORDS.put("it",0);
			STOPWORDS.put("gak",0);
			STOPWORDS.put("ni",0);
			STOPWORDS.put("oh",0);
			STOPWORDS.put("al",0);
			STOPWORDS.put("That",0);
			STOPWORDS.put("How",0);
			STOPWORDS.put("made",0);
			STOPWORDS.put("bisa",0);
			STOPWORDS.put("Kita",0);
			STOPWORDS.put("pero",0);
			STOPWORDS.put("gue",0);
			STOPWORDS.put("kan",0);
			STOPWORDS.put("Que",0);
			STOPWORDS.put("a",0);
			STOPWORDS.put("able",0);
			STOPWORDS.put("about",0);
			STOPWORDS.put("across",0);
			STOPWORDS.put("after",0);
			STOPWORDS.put("all",0);
			STOPWORDS.put("almost",0);
			STOPWORDS.put("also",0);
			STOPWORDS.put("am",0);
			STOPWORDS.put("among",0);
			STOPWORDS.put("an",0);
			STOPWORDS.put("and",0);
			STOPWORDS.put("any",0);
			STOPWORDS.put("are",0);
			STOPWORDS.put("as",0);
			STOPWORDS.put("at",0);
			STOPWORDS.put("be",0);
			STOPWORDS.put("because",0);
			STOPWORDS.put("been",0);
			STOPWORDS.put("but",0);
			STOPWORDS.put("by",0);
			STOPWORDS.put("can",0);
			STOPWORDS.put("cannot",0);
			STOPWORDS.put("could",0);
			STOPWORDS.put("dear",0);
			STOPWORDS.put("did",0);
			STOPWORDS.put("do",0);
			STOPWORDS.put("does",0);
			STOPWORDS.put("either",0);
			STOPWORDS.put("else",0);
			STOPWORDS.put("ever",0);
			STOPWORDS.put("every",0);
			STOPWORDS.put("for",0);
			STOPWORDS.put("from",0);
			STOPWORDS.put("get",0);
			STOPWORDS.put("got",0);
			STOPWORDS.put("had",0);
			STOPWORDS.put("has",0);
			STOPWORDS.put("have",0);
			STOPWORDS.put("he",0);
			STOPWORDS.put("her",0);
			STOPWORDS.put("hers",0);
			STOPWORDS.put("him",0);
			STOPWORDS.put("his",0);
			STOPWORDS.put("how",0);
			STOPWORDS.put("however",0);
			STOPWORDS.put("i",0);
			STOPWORDS.put("if",0);
			STOPWORDS.put("in",0);
			STOPWORDS.put("into",0);
			STOPWORDS.put("is",0);
			STOPWORDS.put("it",0);
			STOPWORDS.put("its",0);
			STOPWORDS.put("just",0);
			STOPWORDS.put("least",0);
			STOPWORDS.put("let",0);
			STOPWORDS.put("like",0);
			STOPWORDS.put("likely",0);
			STOPWORDS.put("may",0);
			STOPWORDS.put("me",0);
			STOPWORDS.put("might",0);
			STOPWORDS.put("most",0);
			STOPWORDS.put("must",0);
			STOPWORDS.put("my",0);
			STOPWORDS.put("neither",0);
			STOPWORDS.put("no",0);
			STOPWORDS.put("nor",0);
			STOPWORDS.put("not",0);
			STOPWORDS.put("of",0);
			STOPWORDS.put("off",0);
			STOPWORDS.put("often",0);
			STOPWORDS.put("on",0);
			STOPWORDS.put("only",0);
			STOPWORDS.put("or",0);
			STOPWORDS.put("other",0);
			STOPWORDS.put("our",0);
			STOPWORDS.put("own",0);
			STOPWORDS.put("rather",0);
			STOPWORDS.put("said",0);
			STOPWORDS.put("say",0);
			STOPWORDS.put("says",0);
			STOPWORDS.put("she",0);
			STOPWORDS.put("should",0);
			STOPWORDS.put("since",0);
			STOPWORDS.put("so",0);
			STOPWORDS.put("some",0);
			STOPWORDS.put("than",0);
			STOPWORDS.put("that",0);
			STOPWORDS.put("the",0);
			STOPWORDS.put("their",0);
			STOPWORDS.put("them",0);
			STOPWORDS.put("then",0);
			STOPWORDS.put("there",0);
			STOPWORDS.put("these",0);
			STOPWORDS.put("they",0);
			STOPWORDS.put("this",0);
			STOPWORDS.put("tis",0);
			STOPWORDS.put("to",0);
			STOPWORDS.put("too",0);
			STOPWORDS.put("twas",0);
			STOPWORDS.put("us",0);
			STOPWORDS.put("wants",0);
			STOPWORDS.put("was",0);
			STOPWORDS.put("we",0);
			STOPWORDS.put("were",0);
			STOPWORDS.put("what",0);
			STOPWORDS.put("when",0);
			STOPWORDS.put("where",0);
			STOPWORDS.put("which",0);
			STOPWORDS.put("while",0);
			STOPWORDS.put("who",0);
			STOPWORDS.put("whom",0);
			STOPWORDS.put("why",0);
			STOPWORDS.put("will",0);
			STOPWORDS.put("with",0);
			STOPWORDS.put("would",0);
			STOPWORDS.put("yet",0);
			STOPWORDS.put("you",0);
			STOPWORDS.put("your",0);
		}
	}
	

	
	  
  }

