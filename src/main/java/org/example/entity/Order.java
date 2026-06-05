package org.example.entity;

import java.time.LocalDateTime;

public interface Order {

    LocalDateTime getOrderDateTime();

    String getOrderNameCompany();

    Integer getOrderAmountOfCement();
}
