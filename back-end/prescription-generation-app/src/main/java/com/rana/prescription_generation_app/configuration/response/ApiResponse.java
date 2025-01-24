package com.rana.prescription_generation_app.configuration.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private T body;
    private Integer totalPages;
    private Long totalElements;
    private Integer currentPage;
    private Integer pageSize;

    private String message;

    public static <T> ApiResponse<T> success(String message, T data,Integer totalPages,Long totalElements,Integer currentPage,Integer pageSize) {
        return ApiResponse.<T>builder()
                .message(message)
                .body(data)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .currentPage(currentPage)
                .pageSize(pageSize)
                .build();
    }

    public static <T> ApiResponse<T> error(String message,T data ) {
        return ApiResponse.<T>builder()
                .message(message)
                .body(data)
                .build();
    }
}




