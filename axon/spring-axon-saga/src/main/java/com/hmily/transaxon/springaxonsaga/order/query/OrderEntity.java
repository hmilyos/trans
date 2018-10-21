package com.hmily.transaxon.springaxonsaga.order.query;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.ZonedDateTime;

@Entity(name = "tb_customer_order")
public class OrderEntity {
    @Id
    private String id;

    private String customerId;

    private String title;

    private String ticketId;

    private Double amount;

    private String status;

    private String reason;

    private ZonedDateTime createdDate;

    public OrderEntity() {
    }

    public OrderEntity(String id, String customerId, String title, String ticketId, Double amount, String status, String reason, ZonedDateTime createdDate) {

        this.id = id;
        this.customerId = customerId;
        this.title = title;
        this.ticketId = ticketId;
        this.amount = amount;
        this.status = status;
        this.reason = reason;
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id='" + id + '\'' +
                ", customerId='" + customerId + '\'' +
                ", title='" + title + '\'' +
                ", ticketId='" + ticketId + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", reason='" + reason + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
