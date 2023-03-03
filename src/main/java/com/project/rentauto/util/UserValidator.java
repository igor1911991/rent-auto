package com.project.rentauto.util;

import com.project.rentauto.model.User;
import com.project.rentauto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    private final UserRepository userRepository;

    @Autowired
    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {

        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object usr, Errors errors) {
        User user = (User) usr;

        if (userRepository.checkUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "", "This username is already in use");
        }

        if (userRepository.checkPhone(user.getPhone()) != null) {
            errors.rejectValue("phone", "", "This phone is already in use");
        }

        if (userRepository.checkEmail(user.getEmail()) != null) {
            errors.rejectValue("email", "", "This email is already in use");
        }

        if (userRepository.checkDriversLicense(user.getDriversLicenseNumber()) != null) {
            errors.rejectValue("driversLicenseNumber", "", "This driver's license is already in use");
        }

    }
}
