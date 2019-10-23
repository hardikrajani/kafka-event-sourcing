package com.demo.kafka.eventSource.serdes;

import com.demo.kafka.eventSource.model.BankAccount;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class BankAccountDeserializer implements Deserializer<BankAccount> {
    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public BankAccount deserialize(String s, byte[] bytes) {

        ObjectMapper mapper = new ObjectMapper();
        BankAccount object = null;
        try {
            object = mapper.readValue(bytes, BankAccount.class);
        } catch (Exception exception) {
            System.out.println("Error in deserializing bytes "+ exception);
        }

        return object;
    }

    @Override
    public void close() {

    }
}
