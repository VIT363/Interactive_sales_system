package org.example.service;

import org.example.exception.OrderParseException;
import org.example.order.OrderImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderParserImpl implements OrderParser {

    public OrderImpl parse(String line) {
        if (line == null || line.isBlank()) {
            throw new OrderParseException("Пустая строка в файле заказов", null);
        }
        String[] parts = line.split("\\|");
        if (parts.length != 3) {
            throw new OrderParseException("Неверный формат строки (ожидается: дата|компания|количество): " + line, null);
        }
        LocalDateTime dateTime = LocalDateTime.parse(parts[0], DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String company = parts[1];
        int amount = Integer.parseInt(parts[2]);
        return new OrderImpl(dateTime, company, amount);
    }
}