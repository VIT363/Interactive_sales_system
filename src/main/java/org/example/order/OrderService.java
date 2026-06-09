package org.example.order;

import org.example.exception.OrderParseException;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OrderService {

    public List<CompanyCost> calculateTotalByCompany(List<OrderImpl> orders,
                                                     int pricePerKg,
                                                     int initialDiscount,
                                                     int discountStep) {
        if (orders == null) {
            throw new OrderParseException("Список заказов не может быть null", null);
        }
        List<OrderImpl> sortedOrders = orders.stream()
                .sorted(Comparator.comparing(OrderImpl::createdDateTime,
                        Comparator.nullsLast(Comparator.naturalOrder())))
                .toList();
        List<CompanyCost> orderCosts = IntStream.range(0, sortedOrders.size())
                .mapToObj(i -> {
                    OrderImpl order = sortedOrders.get(i);
                    int discount = Math.max(0, initialDiscount - i * discountStep);
                    int cost = order.amount() * pricePerKg * (100 - discount) / 100;
                    return new CompanyCost(order.companyName(), cost);
                })
                .toList();

        Map<String, Integer> totalByCompany = orderCosts.stream()
                .collect(Collectors.groupingBy(
                        CompanyCost::company,
                        Collectors.summingInt(CompanyCost::cost)
                ));

        return totalByCompany.entrySet().stream()
                .map(entry -> new CompanyCost(entry.getKey(), entry.getValue()))
                .toList();
    }
}