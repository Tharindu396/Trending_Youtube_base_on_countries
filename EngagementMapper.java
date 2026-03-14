import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

public class EngagementMapper extends Mapper<LongWritable, Text, Text, FloatWritable> {

    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {

        String line = value.toString();
        String[] fields = line.split(",");

        try {

            float views = Float.parseFloat(fields[7]);
            float likes = Float.parseFloat(fields[8]);

            if (views > 0) {

                float engagementRate = likes / views;

                context.write(new Text("engagement"), new FloatWritable(engagementRate));
            }

        } catch (Exception e) {
        }
    }
}