package com.rana.prescription_generation_app.repository;

import com.rana.prescription_generation_app.dto.PrescriptionCountDto;
import com.rana.prescription_generation_app.entity.Prescription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for accessing and manipulating prescription data.
 */
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    /**
     * Finds a prescription by its public ID.
     *
     * @param publicId the public ID of the prescription.
     * @return an Optional containing the prescription if found, or empty if not.
     */
    Optional<Prescription> findByPublicId(String publicId);


    Boolean existsByPublicId(String publicId);

    Page<Prescription> findAll(Specification<Prescription> specification, Pageable pageable);

    @Query(value = "SELECT FORMATDATETIME(p.prescription_date, 'yyyy-MM-dd') AS date, COUNT(*) AS totalPrescriptions " +
            "FROM prescriptions p " +
            "GROUP BY FORMATDATETIME(p.prescription_date, 'yyyy-MM-dd') " +
            "ORDER BY p.prescription_date DESC", nativeQuery = true)
    List<PrescriptionCountDto> findDayWisePrescriptionCount();
}

