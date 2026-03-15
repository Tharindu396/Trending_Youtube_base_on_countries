import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class CategoryPopularityMapper extends Mapper<LongWritable, Text, Text, LongWritable> {

    private String region;
    private Text regionCategoryKey = new Text();
    private LongWritable views = new LongWritable();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {

        FileSplit fileSplit = (FileSplit) context.getInputSplit();
        String fileName = fileSplit.getPath().getName();

        region = fileName.substring(0,2);
    }

    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {

        String line = value.toString();


        if (line.startsWith("video_id"))
            return;


        String[] fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

        if (fields.length < 8)
            return;

        try {

            String categoryId = fields[4];
            long viewCount = Long.parseLong(fields[7]);

            regionCategoryKey.set(region + "\tCategory_" + categoryId);
            views.set(viewCount);

            context.write(regionCategoryKey, views);

        } catch (Exception e) {
            // skip malformed rows
        }
    }
}