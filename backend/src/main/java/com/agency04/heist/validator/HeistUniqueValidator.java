package com.agency04.heist.validator;

import com.agency04.heist.model.Heist;
import com.agency04.heist.service.HeistService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class HeistUniqueValidator implements ConstraintValidator<Unique, Heist> {

    @Autowired
    private HeistService heistService;

    @Override
    public void initialize(Unique constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Heist heist, ConstraintValidatorContext context) {
        if (heistService != null && heistService.findByName(heist.getName()).isPresent()) {
            return false;
        }
        return true;
    }
}