package org.example;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import static org.apache.spark.sql.functions.*;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Main {

    static String[] inputFields = {"id","name","score","score_date"};
    static String[] outputFields = {"id","name","score","score_start_date","score_end_date","system_start_date","system_end_date"};

    static String default_end_date = "9999-12-31";
    static String default_end_date_time = "9999-12-31 23:59:59";
    static String system_date_format = "yyyy-MM-dd";

    static String dataPath = "C:\\Users\\Z8NFF\\Documents\\Projects\\Java\\sample\\bi-temporal\\src\\main\\resources\\data\\";
    static String current_date ="2023-01-01";

    static String inputPath = dataPath + "input\\";
    static String outputPath = dataPath + "output\\";

    public static void main(String[] args) throws Exception {
        Logger.getLogger("org").setLevel(Level.OFF);
        Logger.getLogger("akka").setLevel(Level.OFF);

        DateFormat originalFormat = new SimpleDateFormat(system_date_format, Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = originalFormat.parse(current_date);
        String formattedDate = targetFormat.format(date);

        String inputFileName = inputPath + formattedDate + "000000.csv";
        String outputFileName = outputPath + formattedDate;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);

        String archiveDate = outputPath + targetFormat.format(calendar.getTime());
        //System.out.println(archiveDate);

        SparkSession sparkSession=SparkSession
                .builder()
                .appName("bi-temporal")
                .master("local")
                .config("spark.testing.memory", "471859200")
                .getOrCreate();

        //persist(sparkSession, inputFileName, outputFileName, archiveDate);
        list(sparkSession, outputFileName);
    }

    public static void persist(SparkSession sparkSession, String inputFileName, String outputFileName, String archiveDate) {

        Dataset<Row> input=sparkSession
                .read()
                .format("csv")
                .option("header","true")
                .load(inputFileName)
                .alias("input");
        //input.show();

        File currentDir = new File(outputPath);

        File[] outputFiles = currentDir.listFiles();

        StructType s = new StructType()
                .add(new StructField("id", DataTypes.StringType, true, Metadata.empty()))
                .add(new StructField("name", DataTypes.StringType, true, Metadata.empty()))
                .add(new StructField("score", DataTypes.StringType, true, Metadata.empty()))
                .add(new StructField("score_start_date", DataTypes.StringType, true, Metadata.empty()))
                .add(new StructField("score_end_date", DataTypes.StringType, true, Metadata.empty()))
                .add(new StructField("system_start_date", DataTypes.StringType, true, Metadata.empty()))
                .add(new StructField("system_end_date", DataTypes.StringType, true, Metadata.empty()));

        Dataset<Row> archiveData = sparkSession.read()
                .schema(s)
                .csv(sparkSession.emptyDataset(Encoders.STRING()))
                .alias("archiveData");

        if(Objects.nonNull(outputFiles) && outputFiles.length>0){
            archiveData=sparkSession
                    .read()
                    .format("csv")
                    .option("header","true")
                    .load(archiveDate)
                    .alias("archiveData");
        }

        //archiveData.show(false);
        //to_timestamp(date_sub(date_format(lit(current_date),current_date_format).cast("date"),1)))
        Dataset<Row> common = archiveData
                .join(input, col("archiveData.id").equalTo(col("input.id")), "inner")
                .where(col("score_end_date").equalTo(default_end_date)
                        .and(col("system_end_date").equalTo(default_end_date_time)));
        //common.show(false);

        Dataset<Row> remaining = archiveData.except(common.drop(col("input.id"))
                .drop(col("input.name"))
                .drop(col("input.score"))
                .drop(col("input.score_date")));

        Dataset<Row> commonNew = common
                .withColumn("score_end_date", date_sub(date_format(input.col("score_date"), system_date_format),1))
                .withColumn("system_start_date",to_timestamp(date_format(lit(current_date), system_date_format).cast("date")))
                .withColumn("system_end_date", lit(default_end_date_time))
                .drop(col("input.id"))
                .drop(col("input.name"))
                .drop(col("input.score"))
                .drop(col("input.score_date"));
        //commonNew.show(false);

        Dataset<Row> commonOld = common
                .withColumn("system_end_date",to_timestamp(date_format(lit(current_date), system_date_format)))
                .drop(col("input.id"))
                .drop(col("input.name"))
                .drop(col("input.score"))
                .drop(col("input.score_date"));
        //commonOld.show(false);

        Dataset<Row> outer = input
                .withColumnRenamed("score_date","score_start_date")
                .withColumn("score_end_date", lit(default_end_date))
                .withColumn("system_start_date",to_timestamp(date_format(lit(current_date), system_date_format).cast("date")))
                .withColumn("system_end_date",lit(default_end_date_time));
        //outer.show(false);

        Dataset<Row> output = commonNew.union(commonOld).union(outer).union(remaining);
        //output.show(false);

        output.write().format("csv").option("header","true").save(outputFileName);
    }

    public static void list(SparkSession sparkSession, String fileName){
        Dataset<Row> input=sparkSession
                .read()
                .format("csv")
                .option("header","true")
                .load(fileName);

        input.orderBy( "system_start_date")
                /*.where(col("name").equalTo("Noel"))*/.show(false);
    }
}