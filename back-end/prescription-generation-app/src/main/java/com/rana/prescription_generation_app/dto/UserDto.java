package com.rana.prescription_generation_app.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDto {
    protected String publicId;

    @NotBlank(message = "Username should be required")
    private String username;

    private String email;

    @NotBlank(message = "Password must be required")
    private String password;
}
