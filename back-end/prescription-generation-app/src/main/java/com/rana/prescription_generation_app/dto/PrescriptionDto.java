package com.rana.prescription_generation_app.dto;

import com.rana.prescription_generation_app.enums.Gender;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

/**
 * Data Transfer Object for Prescription.
 *
 * This class is used to transfer prescription data between layers.
 *
 * @author Md Jewel Rana
 * @version 1.0
 * @since 1.0
 */
@Data
public class PrescriptionDto {

    private String publicId;

    @NotNull(message = "Prescription date is mandatory")
    private LocalDate prescriptionDate;

    @NotBlank(message = "Patient name is mandatory")
    @Size(max = 100, message = "Patient name contains maximum 100 characters")
    private String patientName;

    @NotNull(message = "Patient age is mandatory")
    @Min(value = 0, message = "Patient age must be at least 0")
    @Max(value = 150, message = "Patient age must be less than or equal to 150")
    private Integer patientAge;

    @NotNull(message = "Patient gender is mandatory")
    private Gender patientGender;

    private String diagnosis;

    private String medicines;

    private LocalDate nextVisitDate;
}

