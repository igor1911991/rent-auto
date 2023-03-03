package com.project.rentauto.controller;

import com.project.rentauto.exeptions.CreateOrderException;
import com.project.rentauto.model.Auto;
import com.project.rentauto.model.Order;
import com.project.rentauto.model.StatusOrder;
import com.project.rentauto.model.User;
import com.project.rentauto.service.AutoService;
import com.project.rentauto.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/customer")
public class UserController {

    private final OrderService orderService;
    private final AutoService autoService;

    @Autowired
    public UserController(OrderService orderService, AutoService autoService) {
        this.orderService = orderService;
        this.autoService = autoService;
    }


    @GetMapping()
    public String showAuto(Model model) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("autos", autoService.listAvailableAuto());
        model.addAttribute("currentOrderNumber", orderService.currentOrder(user.getId()));

        return "customer/customerPage";
    }

    @PostMapping()
    @Transactional
    public String rentAuto(@ModelAttribute("auto") Auto auto, Model model) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Order order = new Order(user, auto, LocalDateTime.now(), StatusOrder.CREATED);

        Long orderID;

        try {

            orderID = orderService.createOrder(order);
            autoService.blockAuto(auto.getId());
            autoService.setAutoOrderID(orderID, auto.getId());

        } catch (CreateOrderException createOrderException) {

            createOrderException.printStackTrace();
            model.addAttribute("autos", autoService.listAvailableAuto());
            model.addAttribute("currentOrderNumber", orderService.currentOrder(user.getId()));
            model.addAttribute("messageCreateOrderException", createOrderException.getMessage());

            return "customer/customerPage";
        }

        return "redirect:/customer";
    }
}
