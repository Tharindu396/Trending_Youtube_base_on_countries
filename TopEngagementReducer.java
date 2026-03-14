import java.io.IOException;
import java.util.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;

public class TopEngagementReducer extends Reducer<Text, Text, Text, Text> {

    public void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {

        List<String> videos = new ArrayList<>();

        for (Text val : values) {
            videos.add(val.toString());
        }

        Collections.sort(videos, new Comparator<String>() {

            public int compare(String a, String b) {

                float scoreA = Float.parseFloat(a.split("\\|")[1]);
                float scoreB = Float.parseFloat(b.split("\\|")[1]);

                return Float.compare(scoreB, scoreA);
            }
        });

        int limit = Math.min(10, videos.size());

        for (int i = 0; i < limit; i++) {
            context.write(key, new Text(videos.get(i)));
        }
    }
}
