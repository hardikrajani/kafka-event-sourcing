package com.demo.kafka.eventSource.serdes;

import com.demo.kafka.eventSource.model.BankAccount;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class BankAccountSerializer implements Serializer<BankAccount> {


    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public byte[] serialize(String s, BankAccount bankAccount) {
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsString(bankAccount).getBytes();
        } catch (Exception exception) {
            System.out.println("Error in serializing object"+ bankAccount);
        }
        return retVal;
    }

    @Override
    public void close() {

    }
}
