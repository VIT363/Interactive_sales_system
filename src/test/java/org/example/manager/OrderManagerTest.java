package org.example.manager;

import org.example.order.Order;
import org.example.order.OrderInvoice;
import org.example.service.OrderFileManager;
import org.example.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderManagerTest {

    @Mock
    private OrderFileManager orderFileManagerMock;

    @Mock
    private OrderService orderServiceMock;

    @InjectMocks
    private OrderManager orderManager;

    @Test
    void processOrders_shouldCallDependencies() {
        String textInput = "input.txt";
        String textOutput = "output.txt";

        // given
        List<Order> mockOrders = List.of(mock(Order.class));
        List<OrderInvoice> mockInvoices = List.of(mock(OrderInvoice.class));
        when(orderFileManagerMock.readOrders(anyString(), any())).thenReturn(mockOrders);
        when(orderServiceMock.calculateTotalByCompany(eq(mockOrders), anyInt(), anyInt(), anyInt())).thenReturn(mockInvoices);

        // when
        orderManager.processOrders(textInput, textOutput, 10, 50, 5);

        // then
        verify(orderFileManagerMock).readOrders(eq(textInput), any());
        verify(orderServiceMock).calculateTotalByCompany(eq(mockOrders), eq(10), eq(50), eq(5));
        verify(orderFileManagerMock).writeResults(eq(textOutput), eq(mockInvoices));
    }
}
