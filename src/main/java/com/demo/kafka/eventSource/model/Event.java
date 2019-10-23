package com.demo.kafka.eventSource.model;

import java.util.UUID;

public class Event {
    protected int accountId;
    protected String type;
    protected int targetAccount;
    protected int amount;
// {"accountId":"5", "type":"create", "targetAccount":"0", "amount":"0"}
//  {"accountId":"5", "type":"deposit", "targetAccount":"0", "amount":"5000"}
    public int getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(int targetAccount) {
        this.targetAccount = targetAccount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Event{" +
                "accountId=" + accountId +
                ", type='" + type + '\'' +
                ", targetAccount=" + targetAccount +
                ", amount=" + amount +
                '}';
    }
}
