package com.demo.kafka.eventSource.serdes;

import com.demo.kafka.eventSource.model.Event;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class EventSerdes implements Serde<Event> {

    EventSerializer eventSerializer = new EventSerializer();
    EventDeserializer eventDeserializer = new EventDeserializer();

    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public void close() {
        eventDeserializer.close();
        eventSerializer.close();
    }

    @Override
    public Serializer<Event> serializer() {
        return eventSerializer;
    }

    @Override
    public Deserializer<Event> deserializer() {
        return eventDeserializer;
    }
}
