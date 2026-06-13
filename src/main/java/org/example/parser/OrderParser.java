package org.example.parser;

import org.example.order.Order;

public interface OrderParser {

    Order parse(String str);
}
