package com.demo.kafka.eventSource.transformer;

import com.demo.kafka.eventSource.constant.DemoConstants;
import com.demo.kafka.eventSource.model.BankAccount;
import com.demo.kafka.eventSource.model.BankAccountJsonMapper;
import com.demo.kafka.eventSource.model.Event;
import com.demo.kafka.eventSource.model.EventJsonMapper;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;

public class EventValueTransformer implements ValueTransformer<String, String> {

    private KeyValueStore<String, BankAccount> stateStore;
    private ProcessorContext context;

    public EventValueTransformer() {
        super();
    }

    @Override
    public void init(ProcessorContext processorContext) {
        this.context = processorContext;
        System.out.println(this.context);
        this.stateStore = (KeyValueStore) this.context.getStateStore(DemoConstants.statestore);
    }

    @Override
    public String transform(String s) {
        Event event = EventJsonMapper.deserialize(s);
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
        return s;
    }

    @Override
    public String punctuate(long l) {
        return null;
    }

    @Override
    public void close() {

    }
}
