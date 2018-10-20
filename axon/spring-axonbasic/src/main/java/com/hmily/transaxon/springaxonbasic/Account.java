package com.hmily.transaxon.springaxonbasic;

import com.hmily.transaxon.springaxonbasic.command.AccountCreateCommand;
import com.hmily.transaxon.springaxonbasic.command.AccountDepositCommand;
import com.hmily.transaxon.springaxonbasic.command.AccountWithdrawCommand;
import com.hmily.transaxon.springaxonbasic.event.AccountCreatedEvent;
import com.hmily.transaxon.springaxonbasic.event.AccountMoneyDepositedEvent;
import com.hmily.transaxon.springaxonbasic.event.AccountMoneyWithdrawnEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
public class Account {

    @AggregateIdentifier
    private String accountId;

    private Double deposit;

    public Account() {

    }

    @CommandHandler
    public Account(AccountCreateCommand command){
        apply(new AccountCreatedEvent(command.getAccountId(), command.getName()));
    }
    @EventSourcingHandler
    public void on(AccountCreatedEvent event){
        this.accountId = event.getAccountId();
        this.deposit = 0d;
    }

    @CommandHandler
    public void handle(AccountDepositCommand command){
        apply(new AccountMoneyDepositedEvent(command.getAccountId(), command.getAmount()));
    }
    @EventSourcingHandler
    public void on(AccountMoneyDepositedEvent event){
        this.deposit += event.getAmount();
    }

    @CommandHandler
    public void handle(AccountWithdrawCommand command){
        if (deposit >= command.getAmount()) {
            apply(new AccountMoneyWithdrawnEvent(command.getAccountId(), command.getAmount()));
        } else {
            throw new IllegalArgumentException("余额不足");
        }
    }
    @EventSourcingHandler
    public void on(AccountMoneyWithdrawnEvent event){
        this.deposit -= event.getAmount();
    }


    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }
}
