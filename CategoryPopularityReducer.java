import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;

public class CategoryPopularityReducer extends Reducer<Text, LongWritable, Text, LongWritable> {

    private LongWritable totalViews = new LongWritable();

    public void reduce(Text key, Iterable<LongWritable> values, Context context) 
            throws IOException, InterruptedException {
        
        long sum = 0;
        for (LongWritable val : values) {
            sum += val.get(); [cite: 212]
        }
        
        totalViews.set(sum);
        context.write(key, totalViews); [cite: 214]
    }
}