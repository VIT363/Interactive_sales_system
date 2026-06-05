package org.example;

import org.example.service.CalculationOfOrders;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        CalculationOfOrders calculator = new CalculationOfOrders();
        try {
            calculator.processOrders("discount_day_without_ext", "result.txt");
            System.out.println("Результат сохранён в result.txt");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Ошибка при обработке заказов", e);
        }
    }
}