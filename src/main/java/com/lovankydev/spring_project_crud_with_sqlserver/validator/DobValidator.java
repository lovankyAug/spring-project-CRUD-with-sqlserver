package com.lovankydev.spring_project_crud_with_sqlserver.validator;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class DobValidator implements ConstraintValidator<DobConstraint, LocalDate > {

    private int min ;

    @Override
    public void initialize(DobConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        min = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {

        System.out.println("DobValidator called >>> dob = " + localDate);

        if(Objects.isNull(localDate)){
            System.out.println("Dob is null -> return true");
            return true;
        }
        long years = ChronoUnit.YEARS.between(localDate, LocalDate.now());
        System.out.println("Age calculated: " + years);
        return years >= min;
    }
}
