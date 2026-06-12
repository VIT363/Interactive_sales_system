package org.example.manager;

import org.example.order.Order;
import org.example.order.OrderInvoice;
import org.example.service.OrderFileManager;
import org.example.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

    private OrderManager orderManager;

    @BeforeEach
    void setUp() {
        orderManager = new OrderManager(orderFileManagerMock, orderServiceMock);
    }

    @Test
    void processOrders_shouldCallDependencies() {
        // given
        List<Order> mockOrders = List.of(mock(Order.class));
        List<OrderInvoice> mockInvoices = List.of(mock(OrderInvoice.class));
        when(orderFileManagerMock.readOrders(anyString(), any())).thenReturn(mockOrders);
        when(orderServiceMock.calculateTotalByCompany(eq(mockOrders), anyInt(), anyInt(), anyInt())).thenReturn(mockInvoices);
        // when
        orderManager.processOrders("input.txt", "output.txt", 10, 50, 5);
        // then
        verify(orderFileManagerMock).readOrders(eq("input.txt"), any());
        verify(orderServiceMock).calculateTotalByCompany(eq(mockOrders), eq(10), eq(50), eq(5));
        verify(orderFileManagerMock).writeResults(eq("output.txt"), eq(mockInvoices));
    }
}
