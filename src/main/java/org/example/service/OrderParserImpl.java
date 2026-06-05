package org.example.service;

import org.example.entity.OrderImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderParserImpl implements InterfaceAdaptingSeparator {

    public OrderImpl parse(String line) {
        if (line == null || line.isBlank()) {
            throw new IllegalArgumentException("Пустая строка в файле заказов");
        }
        String[] parts = line.split("\\|");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Неверный формат строки (ожидается: дата|компания|количество): " + line);
        }
        LocalDateTime dateTime = LocalDateTime.parse(parts[0], DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String company = parts[1];
        int amount = Integer.parseInt(parts[2]);
        return new OrderImpl(dateTime, company, amount);
    }
}