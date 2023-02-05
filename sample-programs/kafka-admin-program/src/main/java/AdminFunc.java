import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.config.ConfigResource;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class AdminFunc {


    public void createTopics(Properties props) throws IOException, ParseException, ExecutionException, InterruptedException {


        //creating topic list
        List<NewTopic> topics = new ArrayList<>();

        //get Json

        Path fileName = Path.of("C:\\Users\\nhh@kmd.dk\\Desktop\\IdeaProjects\\KafkaAdminProgram\\src\\main\\resources\\topicsExample.json");
        String actual = Files.readString(fileName);

        JSONParser parser = new JSONParser();

        JSONArray a = (JSONArray) parser.parse(new FileReader("C:\\Users\\nhh@kmd.dk\\Desktop\\IdeaProjects\\KafkaAdminProgram\\src\\main\\resources\\topicsExample.json"));

        for (Object o : a)
        {
            JSONObject topic = (JSONObject) o;

            String name = (String) topic.get("topic_name");
            long parti = (long) topic.get("topic_partitions");
            long replicationF = (long) topic.get("topic_replicationFactor");

            //settings to change
            String TOPIC_NAME = name;
            int partitions = (int)parti;
            short replicationFactor = (short)replicationF;

            System.out.println(TOPIC_NAME);
            System.out.println(partitions);
            System.out.println(replicationFactor);

            //add topic to list
            NewTopic nt = new NewTopic(TOPIC_NAME,partitions, replicationFactor);
            topics.add(nt);


        }


        //push all topics to server
        Admin admin = Admin.create(props);
        CreateTopicsResult r = admin.createTopics(topics);
        r.all().get();
    }


    public void printTopics(Properties consumerProps){
        Consumer<String, String> consumer = new KafkaConsumer<>(consumerProps);
        Map<String, List<PartitionInfo>> topics = consumer.listTopics();

        for(String topic : topics.keySet()){
            System.out.println(topic);
        }
    }

    public void deleteTopics(Properties props) throws IOException, ParseException {


        //creating topic list
        List<String> topics = new ArrayList<>();

        //get Json

        Path fileName = Path.of("C:\\Users\\Z6NHH\\IdeaProjects\\KafkaAdminProgram\\src\\main\\resources\\topicsExample.json");
        String actual = Files.readString(fileName);

        JSONParser parser = new JSONParser();

        JSONArray a = (JSONArray) parser.parse(new FileReader("C:\\Users\\Z6NHH\\IdeaProjects\\KafkaAdminProgram\\src\\main\\resources\\topicsExample.json"));

        for (Object o : a) {
            JSONObject topic = (JSONObject) o;

            String name = (String) topic.get("topic_name");

            System.out.println(name);

            //add topic to list
            topics.add(name);

        }


        Admin admin = Admin.create(props);
        DeleteTopicsResult result = admin.deleteTopics(topics);

        try {
            result.all().get(); // wait for finishing
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }


    public void printConfigs(String topic, Properties props) throws ExecutionException, InterruptedException {
        Collection<ConfigResource> cr =  Collections.singleton( new ConfigResource(ConfigResource.Type.TOPIC, topic));

        Admin admin  = Admin.create(props);

        DescribeConfigsResult ConfigsResult = admin.describeConfigs(cr);
        Config all_configs = (Config)ConfigsResult.all().get().values().toArray()[0];

        Iterator ConfigIterator = all_configs.entries().iterator();

        while (ConfigIterator.hasNext())
        {
            ConfigEntry currentConfig = (ConfigEntry) ConfigIterator.next();
            System.out.println(currentConfig.name() + ": " + currentConfig.value());
        }
    }


    public void produceToTopic(Properties producerProps, String topic, String key, String message){
        KafkaProducer<String, String> producer = new KafkaProducer<>(producerProps);
        ProducerRecord<String, String> r = new ProducerRecord<String, String>(topic, key, message);
        producer.send(r);
        producer.flush(); // dumb or smart way of using result.all()
    }

    public void consumeFromTopic(Properties consumerProps, String topic){
        System.out.println("Consuming for 10 seconds...");

        Consumer<String, String> consumer = new KafkaConsumer<>(consumerProps);

        List<String> topics = new ArrayList<>();
        topics.add(topic);
        consumer.subscribe(topics);
        System.out.println("Results: ");

        consumer.poll(Duration.ofMillis(5000));
        consumer.seekToBeginning(consumer.assignment());

        try {
            while(true){
                ConsumerRecords<String, String> cr = consumer.poll(Duration.ofSeconds(10)); //waits up to 10 seconds for new messages - needs initial time to connect .. :/

                if(cr.isEmpty()){
                    break;
                }


                for (ConsumerRecord<String, String> record : cr)
                {
                    System.out.println("#####################" + record.topic() + ": " + record.key() + ": " + record.value() + "#####################");
                }
            }
        }finally {
            consumer.close();
        }



    }






}
