package com.agency04.heist.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Constraint(validatedBy = {HeistUniqueValidator.class, MemberUniqueValidator.class})
@Retention(RUNTIME)
public @interface Unique {
    String message() default "Member already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}