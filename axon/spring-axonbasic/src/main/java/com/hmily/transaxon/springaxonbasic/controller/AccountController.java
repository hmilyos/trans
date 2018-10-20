package com.hmily.transaxon.springaxonbasic.controller;

import com.hmily.transaxon.springaxonbasic.command.AccountCreateCommand;
import com.hmily.transaxon.springaxonbasic.command.AccountDepositCommand;
import com.hmily.transaxon.springaxonbasic.command.AccountWithdrawCommand;
import com.hmily.transaxon.springaxonbasic.domain.AccountEntity;
import com.hmily.transaxon.springaxonbasic.service.AccountProjector;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private AccountProjector accountProjector;

    @PostMapping("")
    public CompletableFuture<Object> createBankAccount(
            @RequestParam String name) {
        log.info("Request to create account for: {}", name);
        String accountId = UUID.randomUUID().toString();
        AccountCreateCommand createAccountCommand = new AccountCreateCommand(accountId, name);
        return commandGateway.send(createAccountCommand);
    }

    @PutMapping("/{accountId}/deposit/{amount}")
    public CompletableFuture<Object> depositMoney(
            @PathVariable String accountId,
            @PathVariable Double amount) {
        log.info("Request to withdraw {} from account {} ", amount, accountId);
        return commandGateway.send(new AccountDepositCommand(
                accountId, amount));
    }

    @PutMapping("/{accountId}/withdraw/{amount}")
    public CompletableFuture<Object> withdrawMoney(@PathVariable String accountId, @PathVariable Double amount) {
        log.info("Request to withdraw {} from account {} ", amount, accountId);
        return commandGateway.send(new AccountWithdrawCommand(accountId, amount));
    }

    @GetMapping("/{accountId}")
    public AccountEntity getAccountById(@PathVariable String accountId) {
        log.info("Request Account with id: {}", accountId);
        return accountProjector.getAccountById(accountId);
    }

    @GetMapping("")
    public List<AccountEntity> getAllAccounts() {
        log.info("Request all Accounts");
        return accountProjector.getAllAccounts();
    }
}