package org.example.order;

import org.example.exception.BadParametersException;

import java.time.LocalDateTime;

public record OrderImpl(LocalDateTime createdDateTime, String companyName, int amount) implements Order {
    public OrderImpl {
        if (createdDateTime == null) {
            throw new BadParametersException("createdDateTime не может быть null");
        }
        if (companyName == null || companyName.isBlank()) {
            throw new BadParametersException("companyName не может быть null или пустым");
        }
        if (amount <= 0) {
            throw new BadParametersException("amount должен быть положительным");
        }
    }
}
