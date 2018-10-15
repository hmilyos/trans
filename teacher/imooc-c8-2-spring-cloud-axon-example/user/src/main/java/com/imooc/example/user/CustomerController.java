package com.imooc.example.user;

import com.imooc.example.user.command.CustomerCreateCommand;
import com.imooc.example.user.command.CustomerDepositCommand;
import com.imooc.example.user.query.CustomerEntity;
import com.imooc.example.user.query.CustomerEntityRepository;
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
@RequestMapping("/customers")
public class CustomerController {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private CustomerEntityRepository customerRepository;

    @PostMapping("")
    public CompletableFuture<Object> create(@RequestParam String name, @RequestParam String password) {
        LOG.info("Request to create account for: {}", name);
        UUID accountId = UUID.randomUUID();
        CustomerCreateCommand createCustomerCommand = new CustomerCreateCommand(accountId.toString(), name, password);
        return commandGateway.send(createCustomerCommand);
    }

    @PutMapping("/{accountId}/deposit/{amount}")
    public CompletableFuture<Object> depositMoney(@PathVariable String accountId, @PathVariable Double amount) {
        LOG.info("Request to withdraw {} from account {} ", amount, accountId);
        return commandGateway.send(new CustomerDepositCommand(accountId, amount));
    }

    @PutMapping("/{accountId}/withdraw/{amount}")
    public CompletableFuture<Object> withdrawMoney(@PathVariable String accountId, @PathVariable Double amount) {
        LOG.info("Request to withdraw {} from account {} ", amount, accountId);
        return commandGateway.send(new CustomerDepositCommand(accountId, amount));
    }


    @GetMapping("/{accountId}")
    public CustomerEntity getCustomerById(@PathVariable String accountId) {
        LOG.info("Request Customer with id: {}", accountId);
        return customerRepository.findOne(accountId);
    }

    @GetMapping("")
    public List<CustomerEntity> getAllCustomers() {
        LOG.info("Request all Customers");
        return customerRepository.findAll();
    }
}
