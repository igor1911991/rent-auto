package com.project.rentauto.controller;

import com.project.rentauto.model.User;
import com.project.rentauto.repository.RoleRepository;
import com.project.rentauto.service.UserService;
import com.project.rentauto.util.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final UserValidator userValidator;

    @Autowired
    public RegistrationController(UserService userService, RoleRepository roleRepository, UserValidator userValidator) {

        this.userService = userService;
        this.roleRepository = roleRepository;
        this.userValidator = userValidator;

    }

    @GetMapping()
    public String getRegistrationPage(Model model) {
        model.addAttribute("newUser", new User());
        model.addAttribute("allRoles", roleRepository.findAll());
        return "registration";
    }

    @PostMapping()
    @Transactional
    public String post(@ModelAttribute("roleList")String [] roles, @ModelAttribute("newUser") @Valid User user, BindingResult bindingResult, Model model) {

        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles", roleRepository.findAll());
            return "registration";
        }

        userService.saveUser(user, roles);
        return "redirect:/login";
    }

}