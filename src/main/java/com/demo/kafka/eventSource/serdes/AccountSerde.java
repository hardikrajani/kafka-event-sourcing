package com.demo.kafka.eventSource.serdes;

import com.demo.kafka.eventSource.model.BankAccount;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class AccountSerde implements Serde<BankAccount> {

    BankAccountSerializer bankAccountSerializer = new BankAccountSerializer();
    BankAccountDeserializer bankAccountDeserializer = new BankAccountDeserializer();

    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public void close() {
        bankAccountDeserializer.close();
        bankAccountSerializer.close();
    }

    @Override
    public Serializer<BankAccount> serializer() {
        return bankAccountSerializer;
    }

    @Override
    public Deserializer<BankAccount> deserializer() {
        return bankAccountDeserializer;
    }
}
