package com.project.rentauto.service;

import com.project.rentauto.exeptions.ChangeStatusAutoException;
import com.project.rentauto.exeptions.CreateOrderException;
import com.project.rentauto.model.Order;
import com.project.rentauto.model.StatusOrder;
import com.project.rentauto.model.User;
import com.project.rentauto.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    private static final long ID = 1L;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;


    @Test
    void currentOrder_shouldReturnCurrentOrderID() {

        when(orderRepository.currentOrder(ID)).thenReturn(ID);

        Long currentOrderID = orderService.currentOrder(ID);

        assertNotNull(currentOrderID);
        assertEquals(currentOrderID, ID);
    }

    @Test
    void createOrder_shouldCallSaveOrder() {

        final Order order = spy(Order.class);
        final User user = spy(User.class);
        user.setId(ID);
        order.setUser(user);
        final Order order1 = spy(Order.class);
        order1.setId(ID);

        when(orderService.currentOrder(order.getUser().getId())).thenReturn(null);
        when(orderRepository.save(order)).thenReturn(order1);
        Long orderID = orderService.createOrder(order);

        assertNotNull(orderID);
        assertEquals(orderID, ID);

    }

    @Test
    void createOrder_shouldReturnCreateOrderException() {

        final OrderServiceImpl orderService = mock(OrderServiceImpl.class);
        final Order order = spy(Order.class);
        final User user = mock(User.class);
        order.setUser(user);

        lenient().when(orderService.currentOrder(ID) != null).thenThrow(CreateOrderException.class);

        assertThrows(CreateOrderException.class, () -> {this.orderService.createOrder(order);});
    }

    @Test
    void newOrders_shouldCallNewOrders() {

        orderService.newOrders();

        verify(orderRepository).newOrders();
    }

    @Test
    void activeOrders_shouldCallIsActive() {

        orderService.activeOrders();

        verify(orderRepository).isActive();
    }

    @Test
    void allOrders_shouldCallFindAll() {

        orderService.allOrders();

        verify(orderRepository).findAll();
    }

    @Test
    void approve_shouldCallApprove() {

        orderService.approve(ID);

        verify(orderRepository).approve(ID, StatusOrder.IN_PROGRESS.name());
    }

    @Test
    void cancel_shouldCallCancel() {

        orderService.cancel(ID);

        verify(orderRepository).cancel(ID, StatusOrder.CANCELLED.name());
    }

    @Test
    void complete_shouldCallComplete() {

        orderService.complete(ID);

        verify(orderRepository).complete(ID, StatusOrder.COMPLETED.name());
    }
}