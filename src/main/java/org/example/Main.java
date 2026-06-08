package org.example;

import org.example.service.OrderManager;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        OrderManager calculator = new OrderManager();
        try {
            calculator.processOrders("discount_day_without_ext", "result.txt", 10, 50, 5);
            System.out.println("Результат сохранён в result.txt");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Ошибка при обработке заказов", e);
        }
    }
}