package com.stylesnap.models; 

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderID")
    private int orderId;

    @Column(name = "UserID", nullable = false)
    private int userId;

    @Column(name = "TotalAmount", nullable = false)
    private double totalAmount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "OrderDate")
    private Date orderDate;
    public int getOrderId() { return orderId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }
}
