package com.lovankydev.spring_project_crud_with_sqlserver.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Constraint(validatedBy = {DobValidator.class})
@Retention(RUNTIME)
public @interface DobConstraint  {

    String message() default  "Your age is invalid.";

    int min();

    Class<?>[] groups() default {};
    Class<? extends Payload> [] payload() default {} ;

}
