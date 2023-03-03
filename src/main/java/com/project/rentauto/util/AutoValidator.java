package com.project.rentauto.util;

import com.project.rentauto.model.Auto;
import com.project.rentauto.repository.AutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AutoValidator implements Validator {

    private final AutoRepository autoRepository;

    @Autowired
    public AutoValidator(AutoRepository autoRepository) {
        this.autoRepository = autoRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Auto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Auto auto = (Auto) target;

        if (autoRepository.modelIsHave(auto.getModel()) != null) {
            errors.rejectValue("model", "", "this model is have");
        }
    }
}
