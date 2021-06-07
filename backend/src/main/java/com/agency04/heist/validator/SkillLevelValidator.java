package com.agency04.heist.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SkillLevelValidator implements ConstraintValidator<SkillLevelValid, String> {

    private static final char LEVEL_CHARACTER = '*';

    @Override
    public void initialize(SkillLevelValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String level, ConstraintValidatorContext context) {
        for (char c : level.toCharArray()) {
            if (c != LEVEL_CHARACTER) {
                return false;
            }
        }
        return true;
    }
}