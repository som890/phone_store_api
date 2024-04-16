package com.dev.phonestore.phonestore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer phoneId;
    private String phoneName;
    private String phoneDescription;
    private Double phoneDiscountedPrice;
    private Double phoneActualPrice;

    public Integer getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Integer phoneId) {
        this.phoneId = phoneId;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public Double getPhoneDiscountedPrice() {
        return phoneDiscountedPrice;
    }

    public void setPhoneDiscountedPrice(Double phoneDiscountedPrice) {
        this.phoneDiscountedPrice = phoneDiscountedPrice;
    }

    public Double getPhoneActualPrice() {
        return phoneActualPrice;
    }

    public void setPhoneActualPrice(Double phoneActualPrice) {
        this.phoneActualPrice = phoneActualPrice;
    }

    public String getPhoneDescription() {
        return phoneDescription;
    }

    public void setPhoneDescription(String phoneDescription) {
        this.phoneDescription = phoneDescription;
    }
}
