package com.agency04.heist.validator;

import com.agency04.heist.model.Heist;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class HeistTimeValidator implements ConstraintValidator<StartTimeBeforeEndTime, Heist> {

    @Override
    public void initialize(StartTimeBeforeEndTime constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Heist heist, ConstraintValidatorContext context) {
        return heist.getStartTime().isBefore(heist.getEndTime());
    }
}