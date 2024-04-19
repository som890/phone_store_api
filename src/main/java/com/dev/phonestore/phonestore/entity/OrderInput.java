package com.dev.phonestore.phonestore.entity;

import java.util.List;

public class OrderInput {
    private String orderFullName;
    private String orderFullAddress;
    private String orderContactNumber;
    private String orderAlternateNumber;
    private List<OrderQuantity> orderQuantityList;

    public OrderInput(String orderFullName, String orderFullAddress, String orderContactNumber, String orderAlternateNumber, List<OrderQuantity> orderQuantityList) {
        this.orderFullName = orderFullName;
        this.orderFullAddress = orderFullAddress;
        this.orderContactNumber = orderContactNumber;
        this.orderAlternateNumber = orderAlternateNumber;
        this.orderQuantityList = orderQuantityList;
    }

    public String getOrderFullName() {
        return orderFullName;
    }

    public void setOrderFullName(String orderFullName) {
        this.orderFullName = orderFullName;
    }

    public String getOrderContactNumber() {
        return orderContactNumber;
    }

    public void setOrderContactNumber(String orderContactNumber) {
        this.orderContactNumber = orderContactNumber;
    }

    public String getOrderFullAddress() {
        return orderFullAddress;
    }

    public void setOrderFullAddress(String orderFullAddress) {
        this.orderFullAddress = orderFullAddress;
    }

    public String getOrderAlternateNumber() {
        return orderAlternateNumber;
    }

    public void setOrderAlternateNumber(String orderAlternateNumber) {
        this.orderAlternateNumber = orderAlternateNumber;
    }

    public List<OrderQuantity> getOrderQuantityList() {
        return orderQuantityList;
    }

    public void setOrderQuantityList(List<OrderQuantity> orderQuantityList) {
        this.orderQuantityList = orderQuantityList;
    }
}
