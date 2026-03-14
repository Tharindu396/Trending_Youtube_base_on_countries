import java.io.IOException;
import java.util.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;

public class TopEngagementReducer extends Reducer<Text, Text, Text, Text> {

    class VideoScore {
        String title;
        float score;

        VideoScore(String t, float s){
            title=t;
            score=s;
        }
    }

    public void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {

        List<VideoScore> list = new ArrayList<>();

        for(Text val : values){

            String v = val.toString();

            if(!v.contains("|")) continue;

            String[] parts = v.split("\\|");

            if(parts.length != 2) continue;

            try{

                float score = Float.parseFloat(parts[1]);
                list.add(new VideoScore(parts[0],score));

            }catch(Exception e){
                continue;
            }
        }

        Collections.sort(list,(a,b)->Float.compare(b.score,a.score));

        int limit = Math.min(3,list.size());

        for(int i=0;i<limit;i++){

            VideoScore vs = list.get(i);

            context.write(key,
                    new Text((i+1)+". "+vs.title+" -> "+vs.score));
        }
    }
}