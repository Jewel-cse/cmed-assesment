package com.rana.prescription_generation_app.exception.classes;

import java.io.Serial;

/**
 * Define CustomUnauthorizedException.
 *
 * @author Md Jewel Rana
 * @version 1.0
 * @since 1.0
 */
public class CustomUnauthorizedException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 499248994881528005L;

    public CustomUnauthorizedException(String message) {
        super(message);
    }
}

