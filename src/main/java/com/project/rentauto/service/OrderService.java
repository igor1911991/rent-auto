package com.project.rentauto.service;

import com.project.rentauto.exeptions.CreateOrderException;
import com.project.rentauto.model.Order;

import java.util.List;

public interface OrderService {
    Long currentOrder(Long userID);
    Long createOrder(Order order) throws CreateOrderException;
    List<Order> newOrders();
    List<Order> activeOrders();
    List<Order> allOrders();
    void approve(Long orderId);
    void cancel(Long orderId);
    void complete(Long orderId);
}
