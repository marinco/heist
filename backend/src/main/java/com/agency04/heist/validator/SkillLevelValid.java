package com.agency04.heist.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Constraint(validatedBy = SkillLevelValidator.class)
@Retention(RUNTIME)
public @interface SkillLevelValid {
    String message() default "Skill level must contain only '*' characters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}