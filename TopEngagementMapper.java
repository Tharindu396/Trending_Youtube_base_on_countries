import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

public class TopEngagementMapper extends Mapper<LongWritable, Text, Text, Text> {

    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {

        String line = value.toString();
        String[] fields = line.split(",");

        try {

            String videoTitle = fields[2];
            float views = Float.parseFloat(fields[7]);
            float likes = Float.parseFloat(fields[8]);
            float comments = Float.parseFloat(fields[10]);
            String country = fields[11];

            if (views > 0) {

                float engagement = (likes + comments) / views;

                context.write(new Text(country),
                        new Text(videoTitle + "|" + engagement));
            }

        } catch (Exception e) {
            // ignore header or bad rows
        }
    }
}
