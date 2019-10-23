package com.demo.kafka.eventSource.model;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EventJsonMapper {

    public static Event deserialize(String value) {

        ObjectMapper mapper = new ObjectMapper();
        Event object = null;
        try {
            object = mapper.readValue(value, Event.class);
        } catch (Exception exception) {
            System.out.println("Error in deserializing bytes "+ exception);
        }
        return object;
    }

    public static String serialize(Event event) {
        String retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsString(event);
        } catch (Exception exception) {
            System.out.println("Error in serializing object"+ event);
        }
        return retVal;
    }


}
