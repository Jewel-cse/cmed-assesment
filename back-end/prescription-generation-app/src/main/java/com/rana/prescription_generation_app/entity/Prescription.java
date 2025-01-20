package com.rana.prescription_generation_app.entity;

import com.rana.prescription_generation_app.constants.SequenceConstants;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The prescription model for the application.
 *
 * This entity represents a prescription and its details.
 *
 * @author Md Jewel Rana
 * @version 1.0
 * @since 1.0
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "PRESCRIPTIONS")
public class Prescription implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,  generator = SequenceConstants.PRESCRIPTION_SEQUENCE)
    @SequenceGenerator(name = SequenceConstants.PRESCRIPTION_SEQUENCE,  initialValue = SequenceConstants.PRESCRIPTION_SEQUENCE_INITIAL_VALUE, allocationSize = SequenceConstants.PRESCRIPTION_SEQUENCE_ALLOCATION)
    private Long id;

    @Column(name = "PUBLIC_ID", unique = true, nullable = false, updatable = false)
    protected String publicId;

    @Column(name = "PRESCRIPTION_DATE", nullable = false)
    private LocalDate prescriptionDate;

    @Column(name = "PATIENT_NAME", nullable = false)
    private String patientName;

    @Column(name = "PATIENT_AGE", nullable = false)
    private Integer patientAge;

    @Column(name = "PATIENT_GENDER", nullable = false)
    private String patientGender;

    @Column(name = "DIAGNOSIS")
    private String diagnosis;

    @Column(name = "MEDICINES")
    private String medicines;

    @Column(name = "NEXT_VISIT_DATE")
    private LocalDate nextVisitDate;

    @CreatedDate
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.publicId = UUID.randomUUID().toString();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}

