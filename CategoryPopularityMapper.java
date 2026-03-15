import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class CategoryPopularityMapper extends Mapper<LongWritable, Text, Text, LongWritable> {

    private Text regionCategoryKey = new Text();
    private LongWritable views = new LongWritable();

    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();

        // Skip the header row [cite: 198]
        if (line.startsWith("video_id")) return;

        // Use a regex split to handle titles that might contain commas
        String[] fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

        if (fields.length > 7) {
            try {
                // Get Region from filename (e.g., "US_videos.csv" -> "US")
                FileSplit fileSplit = (FileSplit) context.getInputSplit();
                String fileName = fileSplit.getPath().getName();
                String region = fileName.substring(0, 2);

                String categoryId = fields[4]; // Column 5 is category_id
                long viewCount = Long.parseLong(fields[7]); // Column 8 is views

                // composite key: Region + Category
                regionCategoryKey.set(region + "\tCategory_" + categoryId);
                views.set(viewCount);

                context.write(regionCategoryKey, views); [cite: 204]
            } catch (Exception e) {
                // Skip malformed rows [cite: 197]
            }
        }
    }
}
