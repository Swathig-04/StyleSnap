package com.stylesnap.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Transaction_Details")
public class TransactionDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TransactionDetailID") 
    private int transactionDetailId;

    @Column(name = "TransactionID", nullable = false)
    private int transactionId;

    @Column(name = "ProductID", nullable = false)
    private int productId;

    @Column(name = "Quantity", nullable = false)
    private int quantity;

    @Column(name = "PriceAtPurchase", nullable = false)
    private double priceAtPurchase;
    public int getTransactionDetailId() {
        return transactionDetailId;
    }

    public void setTransactionDetailId(int transactionDetailId) {
        this.transactionDetailId = transactionDetailId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
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

    public double getPriceAtPurchase() {
        return priceAtPurchase;
    }

    public void setPriceAtPurchase(double priceAtPurchase) {
        this.priceAtPurchase = priceAtPurchase;
    }
}
