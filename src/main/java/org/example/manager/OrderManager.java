package org.example.manager;

import org.example.order.OrderImpl;
import org.example.order.OrderService;
import org.example.order.CompanyCost;
import org.example.parser.ParserFactory;
import org.example.service.OrderFileManager;
import org.example.parser.OrderParser;

import java.util.List;

public class OrderManager {

    private final OrderFileManager orderFileManager;
    private final OrderService orderService;

    public OrderManager(OrderFileManager orderFileManager, OrderService orderService) {
        this.orderFileManager = orderFileManager;
        this.orderService = orderService;
    }

    public void processOrders(String resourceFileName,
                              String outputFile,
                              int pricePerKg,
                              int initialDiscount,
                              int discountStep) {
        OrderParser parser = ParserFactory.createParser(resourceFileName);

        List<OrderImpl> orders = orderFileManager.readOrders(resourceFileName, parser);

        List<CompanyCost> totalByCompany = orderService.calculateTotalByCompany(
                orders, pricePerKg, initialDiscount, discountStep);

        orderFileManager.writeResults(outputFile, totalByCompany);
    }
}