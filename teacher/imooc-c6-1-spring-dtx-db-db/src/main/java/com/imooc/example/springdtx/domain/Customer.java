package com.imooc.example.springdtx.domain;

/**
 * Created by mavlarn on 2018/1/20.
 */
public class Customer {

    private Long id;

    private String username;

    private Integer deposit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getDeposit() {
        return deposit;
    }

    public void setDeposit(Integer deposit) {
        this.deposit = deposit;
    }
}
