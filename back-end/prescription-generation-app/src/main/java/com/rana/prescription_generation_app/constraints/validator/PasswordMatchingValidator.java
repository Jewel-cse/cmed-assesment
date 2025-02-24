package com.rana.prescription_generation_app.constraints.validator;

import com.rana.prescription_generation_app.constraints.PasswordMatching;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;

public class PasswordMatchingValidator implements ConstraintValidator<PasswordMatching, Object> {

    private String passwordFieldName;
    private String confirmPasswordFieldName;

    @Override
    public void initialize(PasswordMatching constraintAnnotation) {
        this.passwordFieldName = constraintAnnotation.password();
        this.confirmPasswordFieldName = constraintAnnotation.confirmPassword();
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        try {
            Field passwordField = obj.getClass().getDeclaredField(passwordFieldName);
            Field confirmPasswordField = obj.getClass().getDeclaredField(confirmPasswordFieldName);

            passwordField.setAccessible(true);
            confirmPasswordField.setAccessible(true);

            String password = (String) passwordField.get(obj);
            String confirmPassword = (String) confirmPasswordField.get(obj);

            if (password == null || !password.equals(confirmPassword)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Passwords do not match")
                        .addPropertyNode(confirmPasswordFieldName)
                        .addConstraintViolation();
                return false;
            }

            return true;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return false;
        }
    }
}
