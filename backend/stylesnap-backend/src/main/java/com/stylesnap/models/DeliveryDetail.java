package com.stylesnap.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "DeliveryDetails")
public class DeliveryDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int deliveryId;
    private int userId;
    private String fullName;
    private String phoneNo;
    private String addressLine;
    private String city;
    private String state;
    private String pincode;
    @Temporal(TemporalType.TIMESTAMP)
    private Date addedAt;
    @PrePersist
    protected void onCreate() {
        this.addedAt = new Date();
    }
    public int getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public Date getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Date addedAt) {
        this.addedAt = addedAt;
    }
}
