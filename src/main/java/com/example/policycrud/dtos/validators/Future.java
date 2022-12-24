package com.example.policycrud.dtos.validators;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = FutureValidator.class)
@Documented
public @interface Future {

    String message() default "Date should be in future";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

