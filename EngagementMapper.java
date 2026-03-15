import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class EngagementMapper extends Mapper<LongWritable, Text, Text, DoubleWritable> {

    private Text country = new Text();

    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {

        String line = value.toString();

        if (line.contains("video_id")) return;

        String[] fields = line.split(",");

        try {

            double views = Double.parseDouble(fields[7]);
            double likes = Double.parseDouble(fields[8]);
            double comments = Double.parseDouble(fields[10]);

            if (views == 0) return;

            double engagement = (likes + comments) / views;

            FileSplit fileSplit = (FileSplit) context.getInputSplit();
            String fileName = fileSplit.getPath().getName();

            String countryCode = fileName.substring(0,2);

            country.set(countryCode);

            context.write(country, new DoubleWritable(engagement));

        } catch(Exception e) {}

    }
}