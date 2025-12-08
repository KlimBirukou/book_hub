package com.klim.birukou.book_hub.controller.dto.user.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.util.Objects;

public class MinAgeValidator implements ConstraintValidator<MinAge, LocalDate> {

    private int minAge;

    @Override
    public void initialize(MinAge constraintAnnotation) {
        this.minAge = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(LocalDate birthdate, ConstraintValidatorContext context) {
        return Objects.isNull(birthdate)
            || birthdate.isBefore(LocalDate.now().minusYears(this.minAge));
    }
}
