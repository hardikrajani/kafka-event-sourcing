package com.demo.kafka.eventSource.model;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BankAccountJsonMapper {

    public static BankAccount deserialize(String value) {

        ObjectMapper mapper = new ObjectMapper();
        BankAccount object = null;
        try {
            object = mapper.readValue(value, BankAccount.class);
        } catch (Exception exception) {
            System.out.println("Error in deserializing bytes "+ exception);
        }
        return object;
    }

    public static String serialize(BankAccount account) {
        String retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsString(account);
        } catch (Exception exception) {
            System.out.println("Error in serializing object"+ account);
        }
        return retVal;
    }
}
