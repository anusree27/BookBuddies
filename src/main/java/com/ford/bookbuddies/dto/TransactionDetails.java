package com.ford.bookbuddies.dto;
public class TransactionDetails {
    private String  transactionId;
    private String currency;
    private Integer amount;
    private String key;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public TransactionDetails(String transactionId, String currency, Integer amount, String key) {
        this.transactionId = transactionId;
        this.currency = currency;
        this.amount = amount;
        this.key = key;
    }
}