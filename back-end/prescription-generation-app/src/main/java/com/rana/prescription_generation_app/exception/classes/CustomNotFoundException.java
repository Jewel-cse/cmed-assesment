package com.rana.prescription_generation_app.exception.classes;
import java.io.Serial;

/**
 * Define CustomNotFoundException.
 *
 * @author Md Jewel Rana
 * @version 1.0
 * @since 1.0
 */
public class CustomNotFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 499248994881528001L;

    public CustomNotFoundException(String message) {
        super(message);
    }
}
