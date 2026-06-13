package org.example.service;

import org.example.exception.OrderParseException;
import org.example.order.Order;
import org.example.order.OrderInvoice;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OrderService {

    public List<OrderInvoice> calculateTotalByCompany(List<Order> orders,
                                                      int pricePerKg,
                                                      int initialDiscount,
                                                      int discountStep) {
        if (orders == null) {
            throw new OrderParseException("Список заказов не может быть null", null);
        }

        List<Order> sortedOrders = orders.stream()
                .sorted(Comparator.comparing(Order::createdDateTime))
                .toList();

        Map<String, Double> totalByCompany = IntStream.range(0, sortedOrders.size())
                .mapToObj(i -> {
                    Order order = sortedOrders.get(i);
                    int discount = Math.max(0, initialDiscount - i * discountStep);
                    double cost = order.amount() * pricePerKg * (100 - discount) / 100.0;
                    return Map.entry(order.companyName(), cost);
                })
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.summingDouble(Map.Entry::getValue)
                ));

        return totalByCompany.entrySet().stream()
                .map(entry -> new OrderInvoice(entry.getKey(), entry.getValue()))
                .toList();
    }
}