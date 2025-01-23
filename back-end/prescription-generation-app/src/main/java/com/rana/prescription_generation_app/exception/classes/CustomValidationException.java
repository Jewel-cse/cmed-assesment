package com.rana.prescription_generation_app.exception.classes;
import java.io.Serial;

/**
 * Define CustomValidationException.
 *
 * @author Md Jewel Rana
 * @version 1.0
 * @since 1.0
 */
public class CustomValidationException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 499248994881528002L;

    public CustomValidationException(String message) {
        super(message);
    }
}
