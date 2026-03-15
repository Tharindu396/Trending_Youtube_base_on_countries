import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.io.Text;

public class CategoryPopularityDriver {

    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf, "Trending Category Popularity by Region");

        job.setJarByClass(CategoryPopularityDriver.class);

        job.setMapperClass(CategoryPopularityMapper.class);
        job.setReducerClass(CategoryPopularityReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        FileInputFormat.addInputPath(job, new Path("/youtube"));
        FileOutputFormat.setOutputPath(job, new Path("/output_CategoryPopularity"));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}