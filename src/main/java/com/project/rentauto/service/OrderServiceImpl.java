package com.project.rentauto.service;

import com.project.rentauto.exeptions.CreateOrderException;
import com.project.rentauto.model.Order;
import com.project.rentauto.model.StatusOrder;
import com.project.rentauto.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Override
    public Long currentOrder(Long userID) {
        return orderRepository.currentOrder(userID);
    }

    @Override
    public Long createOrder(Order order) throws CreateOrderException {

        if(currentOrder(order.getUser().getId()) != null) {
            throw new CreateOrderException();
        }
        Order saveOrder = orderRepository.save(order);
        return saveOrder.getId();
    }

    @Override
    public List<Order> newOrders() {
        return orderRepository.newOrders();
    }

    @Override
    public List<Order> activeOrders() {
        return orderRepository.isActive();
    }

    @Override
    public List<Order> allOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void approve(Long orderId) {
        orderRepository.approve(orderId, StatusOrder.IN_PROGRESS.name());
    }

    @Override
    public void cancel(Long orderId) {
        orderRepository.cancel(orderId, StatusOrder.CANCELLED.name());
    }

    @Override
    public void complete(Long orderId) {
        orderRepository.complete(orderId, StatusOrder.COMPLETED.name());
    }
}
