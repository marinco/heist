package com.agency04.heist.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({TYPE, FIELD})
@Constraint(validatedBy = {MainSkillDtoValidator.class, MainSkillValidator.class})
@Retention(RUNTIME)
public @interface MainSkillValid {
    String message() default "Main skill does not exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}