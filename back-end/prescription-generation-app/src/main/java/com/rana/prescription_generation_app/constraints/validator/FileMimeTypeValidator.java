package com.rana.prescription_generation_app.constraints.validator;

import com.rana.prescription_generation_app.constraints.ValidFileMimeType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public class FileMimeTypeValidator implements ConstraintValidator<ValidFileMimeType, MultipartFile> {

    private List<String> allowedMimeTypes;
    private final Tika tika = new Tika();

    @Override
    public void initialize(ValidFileMimeType constraintAnnotation) {
        allowedMimeTypes = List.of(constraintAnnotation.mimeTypes());
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return false; //consider empty file as invalid
        }

        try {
            String detectedMimeType = tika.detect(file.getInputStream());
            return allowedMimeTypes.contains(detectedMimeType);
        } catch (IOException e) {
            return false;
        }
    }
}
