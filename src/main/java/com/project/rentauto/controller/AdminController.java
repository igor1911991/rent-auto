package com.project.rentauto.controller;

import com.project.rentauto.exeptions.ChangeStatusAutoException;
import com.project.rentauto.model.Auto;
import com.project.rentauto.service.AutoServiceImpl;
import com.project.rentauto.service.OrderService;
import com.project.rentauto.util.AutoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final OrderService orderService;
    private final AutoServiceImpl autoService;
    private final AutoValidator autoValidator;

    @Autowired
    public AdminController(OrderService orderService, AutoServiceImpl autoService, AutoValidator autoValidator) {
        this.orderService = orderService;
        this.autoService = autoService;
        this.autoValidator =autoValidator;
    }


    @GetMapping()
    public String incomingOrders(Model model) {
        model.addAttribute("orderList", orderService.newOrders());
        return "admin/currentOrders";
    }

    @PatchMapping("/approve")
    @Transactional
    public String approve(@ModelAttribute("orderID") Long orderID) {
        orderService.approve(orderID);
        return "redirect:/admin";
    }

    @GetMapping("/active_orders")
    public String showActiveOrders(Model model) {
        model.addAttribute("orderList", orderService.activeOrders());
        return "admin/orderActive";
    }

    @PatchMapping("/active_orders/cancel")
    @Transactional
    public String cancel(@ModelAttribute("autoID") Long autoID, @ModelAttribute("orderID") Long orderID) {
        autoService.setAutoOrderID(0L, autoID);
        autoService.unlockAuto(autoID);
        orderService.cancel(orderID);
        return "redirect:/admin/active_orders";
    }

    @PatchMapping("/active_orders/complete")
    @Transactional
    public String complete(@ModelAttribute("autoID") Long autoID, @ModelAttribute("orderID") Long orderID) {
        autoService.setAutoOrderID(0L, autoID);
        autoService.unlockAuto(autoID);
        orderService.complete(orderID);
        return "redirect:/admin/active_orders";
    }

    @GetMapping("/order_archive")
    public String allOrders(Model model) {
        model.addAttribute("orderList", orderService.allOrders());
        return "admin/orderArchive";
    }


    @GetMapping("/car_park")
    public String carPark(Model model) {
        model.addAttribute("listAuto", autoService.listAuto());
        model.addAttribute("newAuto", new Auto());
        return "admin/carPark";
    }

    @PatchMapping("/car_park/change")
    @Transactional
    public String changeStatusAuto(@ModelAttribute("autoID") Long autoID, Model model) {

        try {
            autoService.changeStatusAuto(autoID);
        } catch (ChangeStatusAutoException e) {
            e.printStackTrace();
            model.addAttribute("listAuto", autoService.listAuto());
            model.addAttribute("newAuto", new Auto());
            model.addAttribute("messageChangeStatusAutoException", e.getMessage());
            return "admin/carPark";

        }
        return "redirect:/admin/car_park";
    }

    @PostMapping("/car_park")
    public String addAuto(@ModelAttribute("newAuto") @Valid Auto auto, BindingResult bindingResult, Model model) {

        autoValidator.validate(auto, bindingResult);

        if(bindingResult.hasErrors()) {
            model.addAttribute("listAuto", autoService.listAuto());
            return "admin/carPark";
        }
        autoService.saveAuto(auto);
        return "redirect:/admin/car_park";
    }
}
