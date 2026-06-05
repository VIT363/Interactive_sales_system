package org.example.service;

import org.example.entity.OrderImpl;

public class OrderParserAdapterImpl implements InterfaceAdaptingSeparator {
    private final InterfaceAdaptingSeparator interfaceAdaptingSeparator;

    public OrderParserAdapterImpl(InterfaceAdaptingSeparator interfaceAdaptingSeparator) {
        this.interfaceAdaptingSeparator = interfaceAdaptingSeparator;
    }

    @Override
    public OrderImpl parse(String str) {
        String converted = str.replace("#", "|");
        return interfaceAdaptingSeparator.parse(converted);
    }
}
