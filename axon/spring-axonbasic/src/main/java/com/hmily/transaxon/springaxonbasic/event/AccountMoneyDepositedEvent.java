package com.hmily.transaxon.springaxonbasic.event;

/**
 * 存钱事件
 */
public class AccountMoneyDepositedEvent {

    private String accountId;

    private Double amount;

    public AccountMoneyDepositedEvent(String accountId, Double amount) {
        this.accountId = accountId;
        this.amount = amount;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
