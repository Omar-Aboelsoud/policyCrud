package com.example.policycrud.dtos.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class FutureValidator  implements ConstraintValidator<Future, LocalDate> {

    @Override
    public void initialize(Future constraintAnnotation) {}

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        return null != value && (value.isEqual(LocalDate.now()) || value.isAfter(LocalDate.now()));
    }
}

