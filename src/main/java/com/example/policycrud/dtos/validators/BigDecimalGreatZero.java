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
@Constraint(validatedBy = BigDecimalGreaterThanZeroValidator.class)
@Documented
public @interface BigDecimalGreatZero {

    String message() default "Value must be grater than 0";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

