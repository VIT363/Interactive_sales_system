package org.example.service;

import org.example.exception.BadParametersException;
import org.example.exception.FileNotFoundException;
import org.example.exception.FileWriteException;
import org.example.exception.OrderParseException;
import org.example.order.Order;
import org.example.order.OrderInvoice;
import org.example.parser.OrderParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class OrderFileManagerTest {

    private final OrderFileManager orderFileManager = new OrderFileManager();

    @Mock
    private OrderParser orderParserMock;

    @TempDir
    Path tempDir;

    @Test
    void readOrders_shouldReadAndParseAllLines() {

        // given
        String str1 = "line1";
        String str2 = "line2";
        Order orderMock = mock(Order.class);
        when(orderParserMock.parse(str1)).thenReturn(orderMock);
        when(orderParserMock.parse(str2)).thenReturn(orderMock);

        // when
        List<Order> result = orderFileManager.readOrders("test_orders.txt", orderParserMock);

        // then
        assertEquals(2, result.size());
        verify(orderParserMock, times(2)).parse(anyString());
        verify(orderParserMock).parse(str1);
        verify(orderParserMock).parse(str2);
    }

    @Test
    void readOrders_whenResourceNotFound_shouldThrowFileNotFoundException() {
        assertThrows(FileNotFoundException.class, () -> orderFileManager.readOrders("missing.txt", orderParserMock));
    }

    @Test
    void readOrders_whenParserFails_shouldThrowOrderParserException() {
        when(orderParserMock.parse(anyString())).thenThrow(new RuntimeException("parse error"));

        assertThrows(OrderParseException.class, () -> orderFileManager.readOrders("test_orders.txt", orderParserMock));
    }

    @Test
    void readOrders_whenFileNameNull_shouldThrowBadParametersException() {
        assertThrows(BadParametersException.class, () -> orderFileManager.readOrders(null, orderParserMock));
    }

    @Test
    void readOrders_whenParserNull_shouldThrowBadParametersException() {
        assertThrows(BadParametersException.class, () -> orderFileManager.readOrders("any.txt", null));
    }

    @Test
    void writeResults_shouldWriteLinesToFile() throws IOException {

        // given
        Path outputPath = tempDir.resolve("result.txt");
        List<OrderInvoice> invoices = List.of(new OrderInvoice("CompanyA", 101.0), new OrderInvoice("CompanyB", 200.0));

        // when
        orderFileManager.writeResults(outputPath.toString(), invoices);

        // then
        List<String> lines = Files.readAllLines(outputPath);
        assertEquals(2, lines.size());
        assertEquals("CompanyA - 101", lines.get(0));
        assertEquals("CompanyB - 200", lines.get(1));
    }

    @Test
    void writeResults_whenFileCannotBeWritten_shouldThrowFileWriteException() {
        String invalidPath = "C:\\invalid\\file.txt";
        List<OrderInvoice> invoices = List.of(new OrderInvoice("CompanyA", 101.0));

        assertThrows(FileWriteException.class, () -> orderFileManager.writeResults(invalidPath, invoices));
    }
}
