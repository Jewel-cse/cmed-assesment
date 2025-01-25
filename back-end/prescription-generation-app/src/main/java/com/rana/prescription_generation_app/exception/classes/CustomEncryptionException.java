package com.rana.prescription_generation_app.exception.classes;

import java.io.Serial;
/**
 * Define CustomEncryptionException
 *
 * @author Md Jewel Rana
 * @version 1.0
 * @since 1.0
 */
public class CustomEncryptionException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 499248994881528003L;

    public CustomEncryptionException(String message) {
        super(message);
    }
}
