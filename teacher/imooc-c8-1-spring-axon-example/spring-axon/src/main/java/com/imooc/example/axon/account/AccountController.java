package com.imooc.example.axon.account;

import com.imooc.example.axon.account.command.AccountCreateCommand;
import com.imooc.example.axon.account.command.AccountDepositCommand;
import com.imooc.example.axon.account.query.AccountEntity;
import com.imooc.example.axon.account.query.AccountEntityRepository;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Created by mavlarn on 2018/5/22.
 */
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private AccountEntityRepository accountEntityRepository;

    @PostMapping("")
    public CompletableFuture<Object> createBankAccount(@RequestParam String name) {
        log.info("Request to create account for: {}", name);
        UUID accountId = UUID.randomUUID();
        AccountCreateCommand createAccountCommand = new AccountCreateCommand(accountId.toString(), name);
        return commandGateway.send(createAccountCommand);
    }

    @PutMapping("/{accountId}/deposit/{amount}")
    public CompletableFuture<Object> depositMoney(@PathVariable String accountId, @PathVariable Double amount) {
        log.info("Request to withdraw {} from account {} ", amount, accountId);
        return commandGateway.send(new AccountDepositCommand(accountId, amount));
    }

    @PutMapping("/{accountId}/withdraw/{amount}")
    public CompletableFuture<Object> withdrawMoney(@PathVariable String accountId, @PathVariable Double amount) {
        log.info("Request to withdraw {} from account {} ", amount, accountId);
        return commandGateway.send(new AccountDepositCommand(accountId, amount));
    }


    @GetMapping("/{accountId}")
    public AccountEntity getAccountById(@PathVariable String accountId) {
        log.info("Request Account with id: {}", accountId);
        return accountEntityRepository.findOne(accountId);
    }

    @GetMapping("")
    public List<AccountEntity> getAllAccounts() {
        log.info("Request all Accounts");
        return accountEntityRepository.findAll();
    }
}
