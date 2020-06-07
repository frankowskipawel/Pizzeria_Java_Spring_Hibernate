package com.sda.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailExistValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailExistConstrains {
    String message() default "Email is already exist in database";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
