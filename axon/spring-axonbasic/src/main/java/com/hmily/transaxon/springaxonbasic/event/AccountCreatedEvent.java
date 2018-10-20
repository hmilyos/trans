package com.hmily.transaxon.springaxonbasic.event;

/**
 * 创建账号事件
 */
public class AccountCreatedEvent {

    private String accountId;
    private String name;

    public AccountCreatedEvent(String accountId, String name) {
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
