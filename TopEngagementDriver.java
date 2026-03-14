import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class TopEngagementDriver {

    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf, "Top 10 Engaging Videos Per Country");

        job.setJarByClass(TopEngagementDriver.class);

        job.setMapperClass(TopEngagementMapper.class);
        job.setReducerClass(TopEngagementReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path("/youtube"));
        FileOutputFormat.setOutputPath(job, new Path("/output_top10"));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
