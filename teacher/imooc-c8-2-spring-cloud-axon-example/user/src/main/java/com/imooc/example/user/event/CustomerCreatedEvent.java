package com.imooc.example.user.event;

/**
 * Created by mavlarn on 2018/5/22.
 */
public class CustomerCreatedEvent {

    private String customerId;
    private String name;
    private String password;

    public CustomerCreatedEvent(String customerId, String name, String password) {
        this.customerId = customerId;
        this.name = name;
        this.password = password;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

}
