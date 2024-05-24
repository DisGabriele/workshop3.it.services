package it.paa.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//validatore per Project per fare in modo che startDate < endDate
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ProjectDatesValidator.class)
public @interface ProjectDates {
    String message() default "start date cannot be after end date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
