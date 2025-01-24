package com.rana.prescription_generation_app.configuration.response;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Global advice to intercept and modify response bodies.
 */
@ControllerAdvice
public class CustomResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // Apply only to classes annotated with @RestController
        return returnType.getContainingClass().isAnnotationPresent(RestController.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {

        // Skip wrapping if the response is already an ApiResponse
        if (body instanceof ApiResponse<?>) {
            return body;
        }

        // Skip wrapping for binary or specific resource types
        if (body instanceof byte[] || body instanceof org.springframework.core.io.Resource ||
                body instanceof String) {
            return body;
        }

        // Handle paginated responses
        if (body instanceof Page<?>) {
            Page<?> page = (Page<?>) body;
            return ApiResponse.success(
                    "Request processed successfully",
                    page.getContent(), // Actual data
                    page.getTotalPages(),
                    page.getTotalElements(),
                    page.getNumber()+1,
                    page.getSize()
            );
        }

        // Wrap all other responses in a success ApiResponse
        return ApiResponse.success("Request processed successfully", body, null, null, null, null);
    }
}




