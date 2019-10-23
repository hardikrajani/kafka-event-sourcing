package com.demo.kafka.eventSource.serdes;

import com.demo.kafka.eventSource.model.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class EventDeserializer implements Deserializer<Event> {
    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public Event deserialize(String s, byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();

        Event event = null;

        try {
            event = mapper.readValue(bytes, Event.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return event;
    }

    @Override
    public void close() {

    }
}
