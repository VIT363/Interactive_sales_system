package org.example.entity;

import java.time.LocalDateTime;

public class OrderImpl implements Order {
    private final LocalDateTime orderDataTime;
    private final String orderNameCompany;
    private final Integer orderAmountOfCement;

    public OrderImpl(LocalDateTime orderDataTime, String orderNameCompany, Integer orderAmountOfCement) {
        this.orderDataTime = orderDataTime;
        this.orderNameCompany = orderNameCompany;
        this.orderAmountOfCement = orderAmountOfCement;
    }

    @Override
    public LocalDateTime getOrderDateTime() {
        return orderDataTime;
    }

    @Override
    public String getOrderNameCompany() {
        return orderNameCompany;
    }

    @Override
    public Integer getOrderAmountOfCement() {
        return orderAmountOfCement;
    }
}