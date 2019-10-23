package com.demo.kafka.eventSource.producer;

import com.demo.kafka.eventSource.constant.DemoConstants;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.Scanner;

public class KafkaProducerDemo {

    private static Scanner in;
    public static void main(String[] argv)throws Exception {

        in = new Scanner(System.in);
        System.out.println("Enter message(type exit to quit)");
        //Configure the Producer
        Properties configProperties = new Properties();
        configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,DemoConstants.bootstrapServer);
        configProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        configProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//        configProperties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CustomPartitioner.class.getName());
        Producer producer = new KafkaProducer(configProperties);
        String line = in.nextLine();
        while(!line.equals("exit")) {

            //TODO: Make sure to use the ProducerRecord constructor that does not take parition Id
            System.out.println("event id :: " + line);
            ProducerRecord rec = new ProducerRecord(DemoConstants.topic, line, line);
            producer.send(rec);
            line = in.nextLine();
        }
        in.close();
        producer.close();
    }
}
