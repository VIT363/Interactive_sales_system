package org.example.service;

import org.example.entity.OrderImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CalculationOfOrders {
    private static final double PRICE_PER_KG = 10.0;
    private static final int START_DISCOUNT = 50;
    private static final int DISCOUNT_STEP = 5;

    public void processOrders(String resourceFileName, String outputFile) throws IOException {
        InterfaceAdaptingSeparator parser = createParser(resourceFileName);

        List<OrderImpl> orders;
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourceFileName)) {
            if (is == null) {
                throw new IllegalArgumentException("Файл не найден в classpath: " + resourceFileName);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                orders = reader.lines()
                        .map(parser::parse)
                        .sorted(Comparator.comparing(OrderImpl::getOrderDateTime))
                        .toList();
            }
        }

        List<OrderWithCost> ordersWithCost = IntStream.range(0, orders.size())
                .mapToObj(i -> {
                    OrderImpl order = orders.get(i);
                    int discount = Math.max(0, START_DISCOUNT - i * DISCOUNT_STEP);
                    double cost = order.getOrderAmountOfCement() * PRICE_PER_KG * (100 - discount) / 100;
                    return new OrderWithCost(order.getOrderNameCompany(), cost);
                })
                .toList();

        Map<String, Double> totalByCompany = ordersWithCost.stream()
                .collect(Collectors.groupingBy(
                        oc -> oc.company,
                        Collectors.summingDouble(oc -> oc.cost)
                ));

        List<String> lines = totalByCompany.entrySet().stream()
                .map(entry -> entry.getKey() + " - " + entry.getValue())
                .collect(Collectors.toList());

        Files.write(Path.of(outputFile), lines);
    }

    private InterfaceAdaptingSeparator createParser(String fileName) {
        if (fileName.endsWith(".txt")) {
            return new OrderParserImpl();
        } else {
            return new OrderParserAdapterImpl(new OrderParserImpl());
        }
    }

    private static class OrderWithCost {
        String company;
        double cost;

        OrderWithCost(String company, double cost) {
            this.company = company;
            this.cost = cost;
        }
    }
}