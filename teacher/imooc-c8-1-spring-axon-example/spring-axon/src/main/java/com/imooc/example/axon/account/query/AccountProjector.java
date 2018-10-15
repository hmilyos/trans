package com.imooc.example.axon.account.query;

import com.imooc.example.axon.account.event.AccountCreatedEvent;
import com.imooc.example.axon.account.event.AccountMoneyDepositedEvent;
import com.imooc.example.axon.account.event.AccountMoneyWithdrawnEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by mavlarn on 2018/5/22.
 */
@Service
public class AccountProjector {

    @Autowired
    private AccountEntityRepository accountEntityRepository;

    @EventHandler
    public void on(AccountCreatedEvent event) {
        AccountEntity account = new AccountEntity(event.getAccountId(), event.getName());
        accountEntityRepository.save(account);
    }

    @EventHandler
    public void on(AccountMoneyDepositedEvent event) {
        String accountId = event.getAccountId();
        AccountEntity accountView = accountEntityRepository.getOne(accountId);
        accountView.setDeposit(accountView.getDeposit() + event.getAmount());
        accountEntityRepository.save(accountView);
    }

    @EventHandler
    public void on(AccountMoneyWithdrawnEvent event) {
        String accountId = event.getAccountId();
        AccountEntity accountView = accountEntityRepository.getOne(accountId);
        accountView.setDeposit(accountView.getDeposit() - event.getAmount());
        accountEntityRepository.save(accountView);
    }
}
