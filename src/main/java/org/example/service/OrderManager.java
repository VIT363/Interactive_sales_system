package org.example.service;

import org.example.order.OrderImpl;
import org.example.order.OrderService;

import java.util.List;
import java.util.Map;

public class OrderManager {

    private final OrderFileManager fileManager = new OrderFileManager();
    private final OrderService orderService = new OrderService();

    public void processOrders(String resourceFileName,
                              String outputFile,
                              int pricePerKg,
                              int initialDiscount,
                              int discountStep) {
        OrderParser parser = ParserFactory.createParser(resourceFileName);

        List<OrderImpl> orders = fileManager.readOrders(resourceFileName, parser);

        Map<String, Double> totalByCompany = orderService.calculateTotalByCompany(
                orders, pricePerKg, initialDiscount, discountStep);

        fileManager.writeResults(outputFile, totalByCompany);
    }
}