import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
public class SentimentMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {
	
    private List<String> positiveList = new ArrayList<>();
    private List<String> negativeList = new ArrayList<>();
    /*
	String positiveList = new String();
	String negativeList = new String();
	*/
    @Override
    public void configure(JobConf job) {
        try {
            FileSystem fs = FileSystem.get(job);
            // Read positive words file
            Scanner positiveReader = new Scanner(fs.open(new Path("positive.txt")));
            while (positiveReader.hasNextLine()) {
                String positivebag = positiveReader.nextLine();
                for (String word : positivebag.split(" ")) {
                   /*
                	positiveList+=word;
                    positiveList+=" ";
                    */
                	positiveList.add(word);
                }
            }
            positiveReader.close();

            // Read negative words file
            Scanner negativeReader = new Scanner(fs.open(new Path("negative.txt")));
            while (negativeReader.hasNextLine()) {
                String negativebag = negativeReader.nextLine();
                for (String word : negativebag.split(" ")) {
                    /*negativeList+=word;
                    negativeList+=" ";
                    */
                	negativeList.add(word);
                }
            }
            negativeReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter)
            throws IOException {
        String line = value.toString();
        String[] splitline = line.split(" ");
        for (String word : splitline) {
            if (positiveList.contains(word)){
            	output.collect(new Text("Sentiment"), new Text("1"));
            	output.collect(new Text("positive"), new Text("1"));
            }
            else if (negativeList.contains(word)){
            	output.collect(new Text("Sentiment"), new Text("-1"));
            	output.collect(new Text("negative"), new Text("1"));
            	
            }
        }
    }
}

