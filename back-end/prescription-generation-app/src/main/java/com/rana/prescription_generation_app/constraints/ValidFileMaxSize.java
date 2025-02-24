package com.rana.prescription_generation_app.constraints;

import com.rana.prescription_generation_app.constraints.validator.FileMaxSizeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {FileMaxSizeValidator.class}
)
public @interface ValidFileMaxSize {
    long maxSize() default 3; // MB

    String message() default "File size exceeded";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
