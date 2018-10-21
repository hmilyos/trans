package com.hmily.transaxon.springaxonsaga.customer.query;

import com.hmily.transaxon.springaxonsaga.customer.event.CustomerChargedEvent;
import com.hmily.transaxon.springaxonsaga.customer.event.CustomerCreatedEvent;
import com.hmily.transaxon.springaxonsaga.customer.event.CustomerDepositedEvent;
import com.hmily.transaxon.springaxonsaga.customer.event.OrderPaidEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerProjector {

    @Autowired
    private CustomerEntityRepository repository;

    @EventHandler
    public void on(CustomerCreatedEvent event) {
        CustomerEntity customer = new CustomerEntity(event.getCustomerId(), event.getName(), event.getPassword(), 0d);
        repository.save(customer);
    }

    @EventHandler
    public void on(CustomerDepositedEvent event) {
        String customerId = event.getCustomerId();
        CustomerEntity accountView = repository.getOne(customerId);

        Double newDeposit = accountView.getDeposit() + event.getAmount();
        accountView.setDeposit(newDeposit);
        repository.save(accountView);
    }

    @EventHandler
    public void on(CustomerChargedEvent event) {
        String customerId = event.getCustomerId();
        CustomerEntity customer = repository.getOne(customerId);

        Double newDeposit = customer.getDeposit() - event.getAmount();
        customer.setDeposit(newDeposit);
        repository.save(customer);
    }

    @EventHandler
    public void on(OrderPaidEvent event) {
        String customerId = event.getCustomerId();
        CustomerEntity customer = repository.getOne(customerId);

        Double newDeposit = customer.getDeposit() - event.getAmount();
        customer.setDeposit(newDeposit);
        repository.save(customer);
    }

    public CustomerEntity getCustomerById(String accountId){
        return repository.findOne(accountId);
    }

    public List<CustomerEntity> getAllCustomers() {
        return repository.findAll();
    }
}
