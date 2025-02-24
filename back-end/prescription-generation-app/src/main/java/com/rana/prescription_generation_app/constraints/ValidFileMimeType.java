package com.rana.prescription_generation_app.constraints;

import com.rana.prescription_generation_app.constraints.validator.FileMimeTypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {FileMimeTypeValidator.class}
)
public @interface ValidFileMimeType {
    String[] mimeTypes() default {};

    String message() default "Invalid file mime type";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
