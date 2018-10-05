package com.hmily.springtransjms.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class CustomerService {
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private PlatformTransactionManager transactionManager;

    @PostConstruct
    public void init(){
        jmsTemplate.setReceiveTimeout(3000);
    }

    @Transactional
    @JmsListener(destination = "customer:msg:new", concurrency = "msgFactory")
    public void handle(String msg){
        log.info("Get JMS message to from customer:{}", msg);
        String reply = "Replied - " + msg;
        jmsTemplate.convertAndSend("customer:msg:reply", reply);
        if (msg.contains("error")){
            simulateError();
        }
    }

    private void simulateError() {
        throw new RuntimeException("some Data error.");
    }

//    注意 concurrency 要和 JmsConfig 里面定义的名字一致
    @JmsListener(destination = "customer:msg2:new", concurrency = "msgFactory")
    public void handle2(String msg) {
        log.info("Get JMS message2 to from customer:{}", msg);
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setTimeout(15);
        TransactionStatus status = transactionManager.getTransaction(def);

        String reply = "Replied-2 - " + msg;
        jmsTemplate.convertAndSend("cutomer:msg:reply", reply);
        if (!msg.contains("error")){
            transactionManager.commit(status);
        } else {
            transactionManager.rollback(status);
        }
    }
}
