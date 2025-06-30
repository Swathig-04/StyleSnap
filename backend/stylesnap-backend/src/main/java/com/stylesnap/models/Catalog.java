package com.stylesnap.models;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Catalog")
public class Catalog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductID")
    private int productID;

    @Column(name = "SKU", nullable = false, unique = true)
    private String sku;

    @Column(name = "ProductName", nullable = false)
    private String productName;

    @Column(name = "Price", nullable = false)
    private double price;

    @Column(name = "Rating")
    private BigDecimal rating;

    @Column(name = "Size")
    private String size;

    @Column(name = "Material")
    private String material;

    @Column(name = "Description")
    private String description;

    @Column(name = "ImageURL")
    private String imageURL;

    @Column(name = "category")
    private String category;

public String getCategory() {
    return category;
}
    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
