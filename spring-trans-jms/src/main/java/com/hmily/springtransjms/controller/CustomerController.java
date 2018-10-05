package com.hmily.springtransjms.controller;

import com.hmily.springtransjms.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private CustomerService customerService;

    @PostMapping("/message1/listen")
    public void createMsgWithListener(@RequestParam String msg) {
        jmsTemplate.convertAndSend("customer:msg:new", msg);
    }

    @PostMapping("/message1/direct")
    public void createMsgDirect(@RequestParam String msg) {
        customerService.handle(msg);
    }

    @GetMapping("/message")
    public String getMsg() {
        Object reply = jmsTemplate.receiveAndConvert("customer:msg:reply");
        return String.valueOf(reply);
    }

    @PostMapping("/message2/listen")
    public void createMsg2WithListener(@RequestParam String msg) {
        jmsTemplate.convertAndSend("customer:msg2:new", msg);
    }

    @PostMapping("/message2/direct")
    public void createMsg2Direct(@RequestParam String msg) {
        customerService.handle2(msg);
    }

}
