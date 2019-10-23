package com.demo.kafka.eventSource.serdes;

import com.demo.kafka.eventSource.model.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class EventSerializer implements Serializer<Event> {
    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public byte[] serialize(String s, Event event) {
        byte[] retVal = null;

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsString(event).getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retVal;
    }

    @Override
    public void close() {

    }
}
