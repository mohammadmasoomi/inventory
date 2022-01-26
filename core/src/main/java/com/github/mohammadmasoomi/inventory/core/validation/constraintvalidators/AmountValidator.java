package com.github.mohammadmasoomi.inventory.core.validation.constraintvalidators;

import com.github.mohammadmasoomi.inventory.core.validation.constraints.Amount;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class AmountValidator implements ConstraintValidator<Amount, BigDecimal> {


    @Override
    public void initialize(Amount constraintAnnotation) {

    }

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        return (value == null || (value.compareTo(BigDecimal.ZERO) > 0) &&
                value.toString().length() <= 16);
    }

}
