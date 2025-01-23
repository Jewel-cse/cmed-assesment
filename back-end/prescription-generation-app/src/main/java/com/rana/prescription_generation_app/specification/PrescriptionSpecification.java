package com.rana.prescription_generation_app.specification;

import com.rana.prescription_generation_app.entity.Prescription;
import com.rana.prescription_generation_app.enums.Gender;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

/**
 * Utility class for creating JPA Specifications for filtering {@link Prescription} entities.
 *
 * <p>This class provides static methods to build {@link Specification} instances for various filtering criteria.</p>
 *
 * @author Md Jewel Rana
 * @version 1.0
 * @since 1.0
 */
public class PrescriptionSpecification {

    /**
     * Creates a Specification for filtering by patient name (case-insensitive and partial match).
     *
     * @param patientName the patient name to filter by.
     * @return a Specification for filtering by patient name.
     */
    public static Specification<Prescription> hasPatientName(String patientName) {
        return (root, query, builder) ->
                patientName == null ? null :
                        builder.like(builder.lower(root.get("patientName")), "%" + patientName.toLowerCase() + "%");
    }

    /**
     * Creates a Specification for filtering by patient gender.
     *
     * @param patientGender the patient gender to filter by.
     * @return a Specification for filtering by patient gender.
     */
    public static Specification<Prescription> hasPatientGender(Gender patientGender) {
        return (root, query, builder) ->
                patientGender == null ? null : builder.equal(root.get("patientGender"), patientGender);
    }

    /**
     * Creates a Specification for filtering by patient age range.
     *
     * @param minAge the minimum age to filter by.
     * @param maxAge the maximum age to filter by.
     * @return a Specification for filtering by patient age range.
     */
    public static Specification<Prescription> hasAgeRange(Integer minAge, Integer maxAge) {
        return (root, query, builder) -> {
            if (minAge == null && maxAge == null) return null;
            if (minAge != null && maxAge != null) {
                return builder.between(root.get("patientAge"), minAge, maxAge);
            }
            if (minAge != null) {
                return builder.greaterThanOrEqualTo(root.get("patientAge"), minAge);
            }
            return builder.lessThanOrEqualTo(root.get("patientAge"), maxAge);
        };
    }

    /**
     * Creates a Specification for filtering by diagnosis (case-insensitive and partial match).
     *
     * @param diagnosis the diagnosis to filter by.
     * @return a Specification for filtering by diagnosis.
     */
    public static Specification<Prescription> hasDiagnosis(String diagnosis) {
        return (root, query, builder) ->
                diagnosis == null ? null :
                        builder.like(builder.lower(root.get("diagnosis")), "%" + diagnosis.toLowerCase() + "%");
    }

    /**
     * Creates a Specification for filtering by prescription date.
     *
     * @param prescriptionDate the prescription date to filter by.
     * @return a Specification for filtering by prescription date.
     */
    public static Specification<Prescription> prescribedOn(LocalDate prescriptionDate) {
        return (root, query, builder) ->
                prescriptionDate == null ? null : builder.equal(root.get("prescriptionDate"), prescriptionDate);
    }

    /**
     * Creates a Specification for filtering by next visit date.
     *
     * @param nextVisitDate the next visit date to filter by.
     * @return a Specification for filtering by next visit date.
     */
    public static Specification<Prescription> hasNextVisitDate(LocalDate nextVisitDate) {
        return (root, query, builder) ->
                nextVisitDate == null ? null : builder.equal(root.get("nextVisitDate"), nextVisitDate);
    }

    /**
     * Combines multiple Specifications using logical AND.
     *
     * @param specs the array of Specifications to combine.
     * @return the combined Specification.
     */
    @SafeVarargs
    public static Specification<Prescription> combine(Specification<Prescription>... specs) {
        Specification<Prescription> result = Specification.where(null);
        for (Specification<Prescription> spec : specs) {
            if (spec != null) {
                result = result.and(spec);
            }
        }

        return result;
    }
}

