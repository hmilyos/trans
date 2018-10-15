package com.imooc.example.axon.account;

import com.imooc.example.axon.account.command.AccountCreateCommand;
import com.imooc.example.axon.account.command.AccountDepositCommand;
import com.imooc.example.axon.account.command.AccountWithdrawCommand;
import com.imooc.example.axon.account.event.AccountCreatedEvent;
import com.imooc.example.axon.account.event.AccountMoneyDepositedEvent;
import com.imooc.example.axon.account.event.AccountMoneyWithdrawnEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

/**
 * Created by mavlarn on 2018/5/22.
 */
@Aggregate
public class Account {

    @AggregateIdentifier
    private String accountId;

    private Double deposit;

    public Account() {
    }

    @CommandHandler
    public Account(AccountCreateCommand command) {
        apply(new AccountCreatedEvent(command.getAccountId(), command.getName()));
    }

    @CommandHandler
    public void handle(AccountDepositCommand command) {
        apply(new AccountMoneyDepositedEvent(command.getAccountId(), command.getAmount()));
    }

    @CommandHandler
    public void handle(AccountWithdrawCommand command) {
        if (deposit >= command.getAmount()) {
            apply(new AccountMoneyWithdrawnEvent(command.getAccountId(), command.getAmount()));
        } else {
            throw new IllegalArgumentException("余额不足");
        }
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        this.accountId = event.getAccountId();
        this.deposit = 0d;
    }

    @EventSourcingHandler
    public void on(AccountMoneyDepositedEvent event) {
        this.deposit += event.getAmount();
    }

    @EventSourcingHandler
    public void on(AccountMoneyWithdrawnEvent event) {
        this.deposit -= event.getAmount();
    }

    public String getAccountId() {
        return accountId;
    }

    public Double getDeposit() {
        return deposit;
    }
}
