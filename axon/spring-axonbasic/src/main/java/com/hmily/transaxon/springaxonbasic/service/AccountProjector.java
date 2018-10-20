package com.hmily.transaxon.springaxonbasic.service;

import com.hmily.transaxon.springaxonbasic.dao.AccountEntityRepository;
import com.hmily.transaxon.springaxonbasic.domain.AccountEntity;
import com.hmily.transaxon.springaxonbasic.event.AccountCreatedEvent;
import com.hmily.transaxon.springaxonbasic.event.AccountMoneyDepositedEvent;
import com.hmily.transaxon.springaxonbasic.event.AccountMoneyWithdrawnEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public AccountEntity getAccountById(String accountId){
        return accountEntityRepository.findOne(accountId);
    }

    public List<AccountEntity> getAllAccounts(){
        return accountEntityRepository.findAll();
    }
}
