package com.demo.kafka.eventSource.util;

import com.demo.kafka.eventSource.constant.DemoConstants;
import com.demo.kafka.eventSource.model.Event;
import com.demo.kafka.eventSource.model.BankAccount;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;

public class KafkaEventHandler implements ValueTransformer<Event, BankAccount> {

    private KeyValueStore<String, BankAccount> stateStore;
    private ProcessorContext context;

    public KafkaEventHandler() {
        super();
    }

    public KafkaEventHandler(KeyValueStore<String, BankAccount> stateStore) {
        super();
        this.stateStore = stateStore;
    }

    public void init(ProcessorContext processorContext) {
        this.context = processorContext;
        System.out.println(this.context);
        this.stateStore = (KeyValueStore) this.context.getStateStore(DemoConstants.statestore);
    }

    @Override
    public BankAccount transform(Event event) {
        String id = String.valueOf(event.getAccountId());
        String type = event.getType();
        BankAccount bankAccount = null;
        switch (type) {
            case "create":
                bankAccount = new BankAccount();
                bankAccount.setAccountId(event.getAccountId());
                bankAccount.setBalance(0);
                stateStore.put(id, bankAccount);
                System.out.println(bankAccount);
                break;
            case "deposit":
                bankAccount = stateStore.get(id);
                bankAccount.setBalance(bankAccount.getBalance() + event.getAmount());
                stateStore.put(id, bankAccount);
                System.out.println(bankAccount);
                break;
            case "withdraw":
                bankAccount = stateStore.get(id);
                bankAccount.setBalance(bankAccount.getBalance() - event.getAmount());
                stateStore.put(id, bankAccount);
                System.out.println(bankAccount);
                break;
        }
        return bankAccount;
    }

    @Override
    public BankAccount punctuate(long l) {
        return null;
    }

    public JsonNode transform(JsonNode jsonNode) {
        JsonNode json = null;
        BankAccount accountSnapshot = null;
        String eventType = jsonNode.get("type").asText();
        final ObjectMapper objectMapper = new ObjectMapper();

        System.out.println("jsonNode : " + jsonNode);

        return null;
    }

    public void close() {

    }
}
