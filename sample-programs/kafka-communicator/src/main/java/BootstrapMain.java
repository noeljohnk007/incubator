import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.util.Properties;

public class BootstrapMain {

    public static void main(String[] args) throws IOException {



        //Keycloak Import settings
        String apiUrlBase   = "https://keycloak1.opus-hcm-dev.cctlabs.kmd.dk";
        String realm        = "keycloak-demo";
        String clientID     = "admin-cli";
        String user         = "api_user";
        String pass         = "123";


        //Kafka Import Settings

        String serverURL = "kafka.opus-hcm-dev.cctlabs.kmd.dk:9094";

        //String topic = args[0];
        String topic = "sampletopic";
        String key = "keycloak_demo"; // **** set key to kc realm *****

        //General Properties
        Properties props = new Properties();

        props.put("bootstrap.servers", serverURL);
        props.put("ssl.enabled.protocols", "TLSv1.2,TLSv1.1,TLSv1");
        props.put("ssl.keystore.location", "C:\\Users\\Z8NFF\\Documents\\Projects\\Java\\sample-programs\\kafka-communicator\\src\\main\\resources\\source.jks");
        props.put("ssl.keystore.password", "j4BTTSP048Kr");
        props.put("ssl.protocol", "TLS");
        props.put("security.protocol", "SSL");
        props.put("sasl.mechanism", "SCRAM-SHA-256");


        //Producer properties
        Properties producerProps = new Properties();
        producerProps.putAll(props);

        producerProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producerProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");



        //init keycloak settings
        KeyCloakAPI ka = new KeyCloakAPI(apiUrlBase, realm, clientID, user, pass);


        //connect og authenticate til keycloak

        try {
            ka.authenticateWithKeycloak();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed To Authenticate");
        }

        //Henter relevant data fra keycloak


        String events = ka.getEvents();


        //create kafka Topic
        //KafkaAdminAPI ad = new KafkaAdminAPI(bootstrapUrl);
        //ad.createTopic("my_topic");


        //Send data til relevant kafka topics

        String message = events;

        KafkaProducer<String, String> producer = new KafkaProducer<>(producerProps);
        ProducerRecord<String, String> r = new ProducerRecord<String, String>(topic, key, message);
        producer.send(r);
        producer.flush(); // interesting way of using result.all()




    }

}
