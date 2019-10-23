package com.demo.kafka.eventSource.util;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

public class CustomPartitioner implements Partitioner {
    private static final int PARTITION_COUNT=50;

    public void configure(Map<String, ?> configs) {
    }

    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        Integer keyInt=Integer.parseInt(key.toString());
        return keyInt % PARTITION_COUNT;
    }

    public void close() {
    }
}
