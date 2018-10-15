package com.imooc.example.axon.customer.query;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by mavlarn on 2018/1/20.
 */
@Entity(name = "tb_customer")
public class CustomerEntity {

    @Id
    private String id;

    @Column(name = "user_name")
    private String username;

    private String password;

    private Double deposit;

    public CustomerEntity() {
    }

    public CustomerEntity(String id, String username, String password, Double deposit) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.deposit = deposit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }
}
