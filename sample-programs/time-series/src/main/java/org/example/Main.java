package org.example;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Main {
    static String dataPath = "C:\\Users\\Z8NFF\\Documents\\Projects\\Java\\sample\\time-series\\src\\main\\resources\\data\\";
    static String inputPath = dataPath + "input\\";
    static String outputPath = dataPath + "output\\";
    static String system_date_format = "yyyy-MM-dd";

    static String current_date ="2022-12-01";
    public static void main(String[] args) throws ParseException {
        Logger.getLogger("org").setLevel(Level.OFF);
        Logger.getLogger("akka").setLevel(Level.OFF);

        SparkSession sparkSession=SparkSession
                .builder()
                .appName("time-series")
                .master("local")
                .config("spark.testing.memory", "471859200")
                .getOrCreate();


        DateFormat originalFormat = new SimpleDateFormat(system_date_format, Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = originalFormat.parse(current_date);
        String formattedDate = targetFormat.format(date);

        String inputData = inputPath + formattedDate;
        String outputData = outputPath + formattedDate;

        //persist(sparkSession,inputData,outputData);
        list(sparkSession,inputData);

    }

    public static void persist(SparkSession sparkSession, String inputData, String outputData){
        Dataset<Row> input=sparkSession
                .read()
                .format("csv")
                .option("header","true")
                .load(inputData);

        input.write().format("csv").option("header","true").save(outputData);
    }
    public static void list(SparkSession sparkSession, String inputData){
        Dataset<Row> input=sparkSession
                .read()
                .format("csv")
                .option("header","true")
                .load(outputPath+"*");

        input.createOrReplaceTempView("credit");

        sparkSession.sql("select * from credit " +
                //"where '2022-11-01 00:00:00' between system_start_date and system_end_date " +
                //"and 2022-10-10 between score_start_date and score_end_date " +
                "order by system_start_date").show(false);
    }
}