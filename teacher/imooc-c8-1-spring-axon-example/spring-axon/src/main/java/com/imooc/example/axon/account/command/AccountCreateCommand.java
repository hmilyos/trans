package com.imooc.example.axon.account.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * Created by mavlarn on 2018/5/22.
 */
public class AccountCreateCommand {

    @TargetAggregateIdentifier
    private String accountId;

    private String name;

    public AccountCreateCommand(String accountId, String name) {
        this.accountId = accountId;
        this.name = name;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
