package com.example.vacation.util.annotation;

import com.example.vacation.util.validator.UniqueUsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueUsernameValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUsername {

    String message() default "Username already exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}