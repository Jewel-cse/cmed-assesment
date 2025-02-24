package com.rana.prescription_generation_app.constraints;

import com.rana.prescription_generation_app.constraints.validator.FileExtensionValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {FileExtensionValidator.class}
)
public @interface ValidFileExtension {
    String[] extensions() default {};

    String message() default "Invalid file extension";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
