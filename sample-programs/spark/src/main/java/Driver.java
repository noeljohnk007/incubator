import org.apache.avro.Schema;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.IntegerType;
import org.apache.spark.sql.types.StringType;
import org.apache.spark.sql.types.StructType;
import static org.apache.spark.sql.functions.*;

public class Driver {
    public static void main(String[] args) {

        SparkSession sparkSession=SparkSession
                .builder()
                .getOrCreate();

        Dataset<Row> input=sparkSession
                .read()
                .format("csv")
                .option("header","true")
                .load(args[0]);

        input.show();
    }
    public void streamData(){
        SparkSession sparkSession=SparkSession
                .builder()
                .getOrCreate();

        Dataset<Row> input=sparkSession.readStream()
                .format("kafka")
                .option("kafka.bootstrap.servers", "192.168.1.100:9092")
                .option("subscribe", "json_topic")
                .load();

        input.printSchema();
        input.show();

        Dataset<Row> personStringDF = input.selectExpr("CAST(value AS STRING)");

        personStringDF.show();

        StructType schema = new StructType()
                .add("id", DataTypes.IntegerType)
                .add("firstname", DataTypes.StringType)
                .add("middlename", DataTypes.StringType)
                .add("lastname", DataTypes.StringType)
                .add("dob_year", DataTypes.IntegerType)
                .add("dob_month", DataTypes.IntegerType)
                .add("gender", DataTypes.StringType)
                .add("salary", DataTypes.IntegerType);

        Dataset<Row> personDF = personStringDF.select(from_json(col("value"), schema).as("data"))
                .select("data.*");

        personDF.show();


    }
}
