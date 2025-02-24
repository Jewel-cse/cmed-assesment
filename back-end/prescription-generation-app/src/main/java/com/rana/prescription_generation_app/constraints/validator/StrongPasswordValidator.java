package com.rana.prescription_generation_app.constraints.validator;

import com.rana.prescription_generation_app.constraints.StrongPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {

    /**
     * Now we consider that a password is strong if its characteristics is -> at least(8 char, 1 uppercase,1 lower case,1 digit,1 special)
     *
     * ^: the start of the string
     * (?=.*\d): at least one digit
     * (?=.*[a-z]): at least one lowercase letter
     * (?=.*[A-Z]): at least one uppercase letter
     * (?=.*[@#$%^&+=!*()]): at least one special character
     * .{8,}: at least 8 characters long
     * $: the end of the string
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        return value.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*()]).{8,}$");
    }
}

