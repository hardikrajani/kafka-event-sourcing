package com.demo.kafka.eventSource.constant;

public class DemoConstants {
    public static String topic = "event-sourcing";
    public static String groupId = "test-consumer-group";
    public static String bootstrapServer = "localhost:9092";
    public static String applicationIdConfig="kafkaDemo";
    public static String statestore = "BankAccountStore";
}
