package com.rana.prescription_generation_app.exception.handler;

import com.rana.prescription_generation_app.exception.classes.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Global Exception Handler for REST Controllers.
 * <p>
 * This class provides centralized exception handling for REST APIs, ensuring
 * consistent error response formatting and logging.
 *
 * @author Md Jewel Rana
 * @version 1.0
 * @since 1.0
 */
@RestControllerAdvice
public class WebRestControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(WebRestControllerAdvice.class);

    /**
     * Handle custom resource not found exceptions.
     *
     * @param ex the CustomNotFoundException.
     * @return a ResponseEntity with error details.
     */
    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomNotFoundException(CustomNotFoundException ex) {
        logger.warn("CustomNotFoundException: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Handle custom already exists exceptions.
     *
     * @param ex the CustomAlreadyExistsException.
     * @return a ResponseEntity with error details.
     */
    @ExceptionHandler(CustomAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleCustomAlreadyExistsException(CustomAlreadyExistsException ex) {
        logger.warn("CustomAlreadyExistsException: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    /**
     * Handle custom Validation exceptions.
     *
     * @param ex the CustomValidationException.
     * @return a ResponseEntity with error details.
     */
    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<ErrorResponse> handleCustomValidationException(CustomValidationException ex) {
        logger.warn("CustomValidationException: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Handle custom Unauthorized exceptions.
     *
     * @param ex the CustomUnauthorizedException.
     * @return a ResponseEntity with error details.
     */
    @ExceptionHandler(CustomUnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleCustomUnauthorizedException(CustomUnauthorizedException ex) {
        logger.warn("CustomUnauthorizedException: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    /**
     * Handle custom Encryption exceptions.
     *
     * @param ex the CustomEncryptionException.
     * @return a ResponseEntity with error details.
     */
    @ExceptionHandler(CustomEncryptionException.class)
    public ResponseEntity<ErrorResponse> handleCustomEncryptionException(CustomEncryptionException ex) {
        logger.error("CustomEncryptionException: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    /**
     * Handle illegal argument exceptions.
     *
     * @param ex the IllegalArgumentException.
     * @return a ResponseEntity with error details.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.warn("IllegalArgumentException: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Handle validation errors from Jakarta Bean Validation.
     *
     * @param ex the MethodArgumentNotValidException.
     * @return a ResponseEntity with validation error details.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        logger.warn("Validation failed: {}", errorMessage);
        ErrorResponse error = new ErrorResponse(errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Handle data integrity errors
     *
     * @param ex the DataIntegrityException.
     * @return a ResponseEntity with error details.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String errorMessage = "Database error: " + (ex.getRootCause() != null
                ? ex.getRootCause().getMessage()
                : "Constraint violation or invalid data.");
        logger.error("Data integrity violation: {}", errorMessage, ex);
        ErrorResponse error = new ErrorResponse(errorMessage);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }


    /**
     * Handle invalid JSON or unreadable request bodies.
     *
     * @param ex the HttpMessageNotReadableException.
     * @return a ResponseEntity with error details.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String message = "Invalid input: " + (ex.getCause() instanceof IllegalArgumentException
                ? ex.getCause().getMessage()
                : "Malformed JSON or incorrect value");
        logger.warn("HttpMessageNotReadableException: {}", message);
        ErrorResponse error = new ErrorResponse(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Handle unexpected exceptions.
     *
     * @param ex the Exception.
     * @return a ResponseEntity with generic error details.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ex.printStackTrace();
        logger.error("Unexpected exception: ", ex.getMessage());
        ErrorResponse error = new ErrorResponse("An unexpected error occurred. Please try again later.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    /**
     * This exception used for  Maximum file upload size
     * @param ex
     * @return
     */

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Map<String, String>> handleMaxSizeException(MaxUploadSizeExceededException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "File size exceeds the maximum allowed limit");

        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(response);
    }
}
