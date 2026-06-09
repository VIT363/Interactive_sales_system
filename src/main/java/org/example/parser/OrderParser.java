package org.example.parser;

import org.example.order.OrderImpl;

public interface OrderParser {

    OrderImpl parse(String str);
}
