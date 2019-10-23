package com.demo.kafka.eventSource.consumer;

import com.demo.kafka.eventSource.constant.DemoConstants;
import com.demo.kafka.eventSource.model.Event;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;

public class KafkaConsumerDemo {

    private static Scanner in;
    public static void main(String[] argv)throws Exception{

        in = new Scanner(System.in);
        ConsumerThread consumerRunnable = new ConsumerThread();
        consumerRunnable.start();
        String line = "";
        while (!line.equals("exit")) {
            line = in.next();
        }
        consumerRunnable.getKafkaConsumer().wakeup();
        System.out.println("Stopping consumer .....");
        consumerRunnable.join();
    }
    private static class ConsumerThread extends Thread{
        private KafkaConsumer<String,Event> kafkaConsumer;

        public void run() {
            Properties configProperties = new Properties();
            configProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            configProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            configProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            configProperties.put(ConsumerConfig.GROUP_ID_CONFIG, DemoConstants.groupId);
            configProperties.put(ConsumerConfig.CLIENT_ID_CONFIG, "simple");
            //Figure out where to start processing messages from
            kafkaConsumer = new KafkaConsumer<String, Event>(configProperties);
            kafkaConsumer.subscribe(Arrays.asList(DemoConstants.topic));
            //Start processing messages
            try {
                while (true) {
                    ConsumerRecords<String, Event> records = kafkaConsumer.poll(100);
                    for (ConsumerRecord<String, Event> record : records) {
                        System.out.println(record);
                    }

                }
            }catch(WakeupException ex){
                System.out.println("Exception caught " + ex.getMessage());
            }finally{
                kafkaConsumer.close();
                System.out.println("After closing KafkaConsumer");
            }
        }
        public KafkaConsumer<String,Event> getKafkaConsumer(){
            return this.kafkaConsumer;
        }
    }
}
