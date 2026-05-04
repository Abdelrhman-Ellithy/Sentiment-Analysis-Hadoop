import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class SentimentReducer extends MapReduceBase implements Reducer<Text, Text, Text, Text> {

    public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter)
            throws IOException {	
        int cnt = 0;
        while (values.hasNext()) {
            int number = Integer.parseInt(values.next().toString());
            cnt += number;
        }
        if(key.toString().equals("positive")){
        	
        	String num=Integer.toString(cnt);
        	output.collect(key, new Text(num));
        	
        }
        else if(key.toString().equals("negative")){
        	
        	String num=Integer.toString(cnt);
        	output.collect(key, new Text(num));
        	
        }
        else if(key.toString().equals("Sentiment")){
            if(cnt>0){
            	output.collect(key, new Text("positive"));
            }
            else if (cnt==0){
            	output.collect(key, new Text("natural"));
            }
            else{
            	output.collect(key, new Text("negative"));
            }
        }
    }
}

