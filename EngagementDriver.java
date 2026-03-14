import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class EngagementDriver {

    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "YouTube Engagement");

        job.setJarByClass(EngagementDriver.class);

        job.setMapperClass(EngagementMapper.class);
        job.setReducerClass(EngagementReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FloatWritable.class);

        FileInputFormat.addInputPath(job, new Path("/youtube"));
        FileOutputFormat.setOutputPath(job, new Path("/output_engagement"));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}