package org.example.service;

import org.example.order.OrderImpl;

public interface OrderParser {

    OrderImpl parse(String str);
}
