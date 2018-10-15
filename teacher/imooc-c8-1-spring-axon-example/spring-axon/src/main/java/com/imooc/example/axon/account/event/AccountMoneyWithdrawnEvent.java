package com.imooc.example.axon.account.event;


/**
 * Created by mavlarn on 2018/5/22.
 */
public class AccountMoneyWithdrawnEvent {

    private String accountId;

    private Double amount;

    public AccountMoneyWithdrawnEvent(String accountId, Double amount) {
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
