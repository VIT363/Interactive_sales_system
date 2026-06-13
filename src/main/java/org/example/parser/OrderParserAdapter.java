package org.example.parser;

import org.example.order.Order;

public class OrderParserAdapter implements OrderParser {
    private final OrderParser orderParser;

    public OrderParserAdapter(OrderParser orderParser) {
        this.orderParser = orderParser;
    }

    @Override
    public Order parse(String str) {
        String converted = str.replace("#", "|");
        return orderParser.parse(converted);
    }
}
