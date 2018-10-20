package com.hmily.transaxon.springaxonbasic.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * 创建账号
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
