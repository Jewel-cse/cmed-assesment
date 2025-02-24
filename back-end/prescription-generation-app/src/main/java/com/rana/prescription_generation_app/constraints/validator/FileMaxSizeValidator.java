package com.rana.prescription_generation_app.constraints.validator;

import com.rana.prescription_generation_app.constraints.ValidFileMaxSize;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

public class FileMaxSizeValidator implements ConstraintValidator<ValidFileMaxSize, MultipartFile> {

    private long maxSizeInBytes;

    @Override
    public void initialize(ValidFileMaxSize constraintAnnotation) {
        maxSizeInBytes = constraintAnnotation.maxSize() * 1024 * 1024;
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {
        if (file == null || file.isEmpty()) {
            return true;
        }
        if (file.getSize() > maxSizeInBytes) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("File size must be less than " + (maxSizeInBytes / (1024 * 1024)) + "MB")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}