package com.imooc.example.axon.ticket.query;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 使用JPA的entity作为aggregate，文档中没有说明要加@Aggregate，实际上不加的话会出错。
 */
@Entity(name = "tb_ticket")
public class TicketEntity {

    @Id
    private String id;

    private String name;

    private String lockUser;

    private String owner;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLockUser() {
        return lockUser;
    }

    public String getOwner() {
        return owner;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLockUser(String lockUser) {
        this.lockUser = lockUser;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
