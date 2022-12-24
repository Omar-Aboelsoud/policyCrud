package com.example.policycrud.dtos.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class BigDecimalGreaterThanZeroValidator implements ConstraintValidator<BigDecimalGreatZero , BigDecimal> {
    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        return value.compareTo(BigDecimal.ZERO) > 0;
    }
}
