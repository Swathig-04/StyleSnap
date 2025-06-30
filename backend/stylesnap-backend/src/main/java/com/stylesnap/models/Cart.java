package com.stylesnap.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartId;

    @Column(nullable = false)
    private int userId;

    @Column(nullable = false)
    private int productId;

    @Column(nullable = false)
    private int quantity;

    @JsonProperty("size")
    @Column(name = "cart_size", nullable = false)
    private String size;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "added_at")
    private Date addedAt;

    public Cart() {
    }

    @PrePersist
    protected void onCreate() {
        this.addedAt = new Date();
    }
    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Date getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Date addedAt) {
        this.addedAt = addedAt;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", userId=" + userId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", size='" + size + '\'' +
                ", addedAt=" + addedAt +
                '}';
    }
}
