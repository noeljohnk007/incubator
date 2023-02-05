import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;


public class Main {



    public static void main(String[] args) throws IOException, ParseException, ExecutionException, InterruptedException {

       //Kafka Settings

        // URL jaas = Main.class.getResource("real_jaas.conf");                      // enable for debug
        //System.setProperty("java.security.auth.login.config", jaas.getPath());    // enable for debug
        //BasicConfigurator.configure();                                            // enable for debug

        String serverURL = "kafka.opus-hcm-dev.cctlabs.kmd.dk:9094";

        //General Properties
        Properties props = new Properties();

        props.put("bootstrap.servers", serverURL);
        props.put("ssl.enabled.protocols", "TLSv1.2,TLSv1.1,TLSv1");
        props.put("ssl.keystore.location", "C:\\source1.jks");
        props.put("ssl.keystore.password", "j4BTTSP048Kr");
        props.put("ssl.protocol", "TLS");
        props.put("security.protocol", "SSL");
        props.put("sasl.mechanism", "SCRAM-SHA-256");

        //Consumer properties
        Properties consumerProps = new Properties();
        consumerProps.putAll(props);

        consumerProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.put("group.id", "adminClient");
        consumerProps.put("auto.offset.reset", "earliest");

        //Producer properties
        Properties producerProps = new Properties();
        producerProps.putAll(props);

        producerProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producerProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");



        //create topics
        AdminFunc adminCommand = new AdminFunc();
        Scanner scan = new Scanner(System.in);



        while(true){

            System.out.println("Target Server: " + serverURL);
            System.out.println("1. CreateTopics in topicsExample.json...");
            System.out.println("2. Delete Topics in topicsExample.json...");
            System.out.println("3. List All Topics...");
            System.out.println("4. Print topic configuration...");
            System.out.println("5. Produce to Topic...");
            System.out.println("6. Consume from Topic...");



            int option = scan.nextInt();

            switch(option){
                case 1:
                    adminCommand.createTopics(props);
                    break;
                case 2:
                    adminCommand.deleteTopics(props);
                    break;
                case 3:
                    adminCommand.printTopics(consumerProps);
                    break;
                case 4:
                    scan.nextLine();
                    System.out.println("Topic name: ");
                    String z = scan.nextLine();
                    adminCommand.printConfigs(z, props);
                    break;
                case 5:
                    scan.nextLine();
                    System.out.println("Produce to Topic name: ");
                    String x = scan.nextLine();
                    System.out.println("Type message: ");
                    String message = scan.nextLine();
                    adminCommand.produceToTopic(producerProps, x, "info", message);
                    break;
                case 6:
                    scan.nextLine();
                    System.out.println("Consume from Topic name: ");
                    String n = scan.nextLine();
                    adminCommand.consumeFromTopic(consumerProps, n);
                    break;

            }

        }






    }






}
