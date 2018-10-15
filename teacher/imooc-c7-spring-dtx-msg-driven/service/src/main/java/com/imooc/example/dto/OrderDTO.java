package com.imooc.example.dto;

/**
 * Created by mavlarn on 2018/3/28.
 */
public class OrderDTO {

    private Long id;

    private String uuid;

    private Long customerId;

    private String title;

    private Long ticketNum;

    private int amount;

    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTicketNum() {
        return ticketNum;
    }

    public void setTicketNum(Long ticketNum) {
        this.ticketNum = ticketNum;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", customerId=" + customerId +
                ", title='" + title + '\'' +
                ", ticketNum=" + ticketNum +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                '}';
    }
}
