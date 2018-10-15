package com.imooc.example.springdtx.web;

import com.imooc.example.springdtx.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * Created by mavlarn on 2018/1/20.
 */
@RestController
@RequestMapping("/api/customer")
public class CustomerResource {

    @Autowired
    JmsTemplate jmsTemplate;
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

    @PostMapping("/message2/listen")
    public void createMsg2WithListener(@RequestParam String msg) {
        jmsTemplate.convertAndSend("customer:msg2:new", msg);
    }
    @PostMapping("/message2/direct")
    public void createMsg2Direct(@RequestParam String msg) {
        customerService.handle2(msg);
    }

    @GetMapping("/message")
    public String getMsg() {
        Object reply = jmsTemplate.receiveAndConvert("customer:msg:reply");
        return String.valueOf(reply);
    }
}
