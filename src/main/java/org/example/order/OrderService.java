package org.example.order;

import org.example.exception.OrderParseException;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OrderService {

    public Map<String, Double> calculateTotalByCompany(List<OrderImpl> orders,
                                                       int pricePerKg,
                                                       int initialDiscount,
                                                       int discountStep) {
        if (orders == null) {
            throw new OrderParseException("Список заказов не может быть null", null);
        }
        List<OrderImpl> sortedOrders = orders.stream()
                .sorted(Comparator.comparing(OrderImpl::createdDateTime))
                .toList();

        return IntStream.range(0, sortedOrders.size())
                .mapToObj(i -> {
                    OrderImpl order = sortedOrders.get(i);
                    int discount = Math.max(0, initialDiscount - i * discountStep);
                    double cost = order.amount() * pricePerKg * (100 - discount) / 100.0;
                    return Map.entry(order.companyName(), cost);
                })
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.summingDouble(Map.Entry::getValue)
                ));
    }
}