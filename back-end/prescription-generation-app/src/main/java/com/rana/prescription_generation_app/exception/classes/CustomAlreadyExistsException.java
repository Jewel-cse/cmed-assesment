package com.rana.prescription_generation_app.exception.classes;
import java.io.Serial;

/**
 * Define CustomAlreadyExistsException.
 *
 * @author Md Jewel Rana
 * @version 1.0
 * @since 1.0
 */
public class CustomAlreadyExistsException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 499248994881528000L;

    public CustomAlreadyExistsException(String message) {
        super(message);
    }
}
