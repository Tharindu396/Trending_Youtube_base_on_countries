import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class TopEngagementMapper extends Mapper<LongWritable, Text, Text, Text> {

    private String country;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {

        FileSplit fileSplit = (FileSplit) context.getInputSplit();
        String fileName = fileSplit.getPath().getName();

        country = fileName.substring(0,2); 
    }

    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {

        String line = value.toString();

        if (line.startsWith("video_id")) {
            return;
        }

        String[] fields = line.split(",");


        if (fields.length < 11) return;

        try {

            String title = fields[2];

            String viewsStr = fields[7];
            String likesStr = fields[8];
            String commentsStr = fields[10];

            if(!viewsStr.matches("\\d+") ||
               !likesStr.matches("\\d+") ||
               !commentsStr.matches("\\d+"))
                return;

            float views = Float.parseFloat(viewsStr);
            float likes = Float.parseFloat(likesStr);
            float comments_count = Float.parseFloat(commentsStr);

            if (views == 0) return;

            float engagement = (likes + comments_count) / views;

            context.write(new Text(country),
                    new Text(title + "|" + engagement));

        } catch (Exception e) {
            
        }
    }
}