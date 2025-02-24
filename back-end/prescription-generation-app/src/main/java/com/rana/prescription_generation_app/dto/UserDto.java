package com.rana.prescription_generation_app.dto;

import com.rana.prescription_generation_app.constraints.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@PasswordMatching(password = "password",confirmPassword = "confirmPassword",message = "Password not matched")
public class UserDto {
    protected String publicId;

    @NotBlank(message = "Username should be required")
    private String username;

    @Email
    private String email;

    @NotBlank(message = "Password must be required")
    @StrongPassword
    private String password;

    @NotBlank(message = "Confirm password must be required")
    private String confirmPassword;

    @NotNull
    @ValidFileMaxSize(maxSize = 100)
    @ValidFileExtension(extensions = {"pdf"})
    @ValidFileMimeType(mimeTypes = {"application/pdf"})
    private MultipartFile testFile;
}
