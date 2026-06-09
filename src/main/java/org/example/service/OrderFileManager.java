package org.example.service;

import org.example.exception.*;
import org.example.order.OrderImpl;
import org.example.order.CompanyCost;
import org.example.parser.OrderParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class OrderFileManager {

    public List<OrderImpl> readOrders(String resourceFileName, OrderParser parser) {
        if (resourceFileName == null) {
            throw new BadParametersException("Имя файла ресурса не может быть null");
        }
        if (parser == null) {
            throw new BadParametersException("OrderParser не может быть null" + resourceFileName);
        }
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourceFileName)) {
            if (is == null) {
                throw new FileNotFoundException("Файл не найден в classpath");
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                return reader.lines()
                        .map(line -> {
                            try {
                                return parser.parse(line);
                            } catch (Exception e) {
                                throw new OrderParseException("Ошибка парсинга строки: " + line, e);
                            }
                        })
                        .collect(Collectors.toList());
            } catch (IOException e) {
                throw new FileReadException("Ошибка чтения файла: " + resourceFileName, e);
            }
        } catch (IOException e) {
            throw new FileReadException("Не удалось открыть поток ресурса: " + resourceFileName, e);
        }
    }

    public void writeResults(String outputFile, List<CompanyCost> totalByCompany) {
        List<String> lines = totalByCompany.stream()
                .map(CompanyCost::toString)
                .collect(Collectors.toList());
        try {
            Files.write(Path.of(outputFile), lines);
        } catch (IOException e) {
            throw new FileWriteException("Ошибка записи результата в файл: " + outputFile, e);
        }
    }
}