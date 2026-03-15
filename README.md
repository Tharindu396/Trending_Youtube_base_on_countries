# YouTube Data Analysis using Hadoop MapReduce
**Module:** Cloud Computing (EE7222/EC7204)  
**University:** University of Ruhuna - Faculty of Engineering

## 📌 Project Overview
This project implements a custom MapReduce job to analyze the **Trending YouTube Video Statistics** dataset. The primary goal is to determine **"Trending Category Popularity by Region"** by aggregating total views for specific video categories across different geographic regions (US, GB, CA, etc.).

## 📊 Dataset
* **Source:** [Kaggle - Trending YouTube Video Statistics](https://www.kaggle.com/datasets/jawadaahmed/trending-youtube-video-statistics)
* **Format:** Multi-region CSV files (e.g., `USvideos.csv`, `GBvideos.csv`).
* [cite_start]**Scale:** Includes over 100,000 rows of real-world trending data[cite: 304].
* **Key Columns Used:**
    * `category_id`: Used for grouping video types.
    * `views`: Used as the metric for popularity.
    * **Filename**: Parsed to identify the region.

## ⚙️ Architecture
The project utilizes the Hadoop MapReduce framework to process data in parallel:

1. **Mapper (`CategoryPopularityMapper.java`)**: 
   * Reads the CSV input line-by-line.
   * Extracts the `category_id` and `views`.
   * Identifies the **Region** by parsing the input file name using `FileSplit`.
   * Emits a composite key: `(Region + CategoryID)` and the value `(Views)`.

2. **Shuffle & Sort**: 
   * [cite_start]Hadoop groups all view counts belonging to the same Region-Category pair[cite: 135].

3. **Reducer (`CategoryPopularityReducer.java`)**: 
   * [cite_start]Aggregates the total view count for each key[cite: 154].
   * Emits the final total popularity metrics.



## 🚀 Execution Steps

### 1. Environment Setup
* [cite_start]Ensure **Hadoop 3.x** is installed and running (HDFS & YARN)[cite: 325].
* Ensure **Java JDK 8+** is configured.

### 2. Prepare HDFS
```bash
# Create input directory in HDFS
hdfs dfs -mkdir -p /user/youtube/input

# Upload dataset CSV files to HDFS
hdfs dfs -put *.csv /user/youtube/input/

### 3. Compile and Package
```bash
# Compile the Java classes
hadoop com.sun.tools.javac.Main CategoryPopularity*.java

# Create the JAR file
jar cf youtube_analysis.jar CategoryPopularity*.class
# Execute the MapReduce job
# The arguments are: [DriverClass] [InputPath] [OutputPath]
hadoop jar youtube_analysis.jar CategoryPopularityDriver /user/youtube/input /user/youtube/output

# View the output stored in HDFS
hdfs dfs -cat /user/youtube/output/part-r-00000
