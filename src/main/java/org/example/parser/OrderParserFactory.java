package org.example.parser;

public class  OrderParserFactory {

    public static OrderParser createParser(String fileName) {
        if (fileName.endsWith(".txt")) {
            return new OrderParserImpl();
        } else {
            return new OrderParserAdapter(new OrderParserImpl());
        }
    }
}
