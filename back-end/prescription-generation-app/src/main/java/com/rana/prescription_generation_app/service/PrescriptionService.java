package com.rana.prescription_generation_app.service;

import com.rana.prescription_generation_app.dto.PrescriptionDto;
import com.rana.prescription_generation_app.entity.Prescription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

/**
 * Service interface for managing prescriptions.
 *
 * Provides method definitions for CRUD operations. Each method returns DTOs to ensure that data
 * returned to the client is in the appropriate form and hides internal entity details.
 *
 * @Author Md Jewel Rana
 * @version 1.0
 * @since 1.0
 */
public interface PrescriptionService {

    /**
     * Create a new prescription.
     *
     * This method creates a new prescription in the database based on the provided `PrescriptionDto`.
     * Before saving, it checks if a prescription with the given `publicId` already exists. If so, an
     * exception is thrown to avoid duplicate entries.
     *
     * @param prescriptionDto the DTO containing the prescription data.
     * @return the created `PrescriptionDto` object after it is saved in the database.
     * @throws RuntimeException if a prescription with the given publicId already exists.
     */
    PrescriptionDto createPrescription(PrescriptionDto prescriptionDto);

    /**
     * Retrieve a prescription by its unique public ID.
     *
     * This method retrieves a prescription from the database based on the provided publicId.
     * If the prescription is found, it is returned as a `PrescriptionDto`. If not, an exception
     * is thrown.
     *
     * @param publicId the unique public ID of the prescription to retrieve.
     * @return the retrieved `PrescriptionDto` containing prescription details.
     * @throws RuntimeException if a prescription with the given publicId is not found.
     */
    PrescriptionDto getPrescriptionById(String publicId);

    /**
     * Retrieve all prescriptions based on a specification and pageable request.
     *
     * This method allows querying prescriptions using a dynamic specification, which can be built
     * to include various filtering criteria. It returns a paginated list of prescriptions.
     *
     * @param specification the specification that defines the query filters.
     * @param pageable the pageable object that controls pagination settings.
     * @return a page of `PrescriptionDto` objects matching the specification and pagination settings.
     */
    Page<PrescriptionDto> getAllPrescriptions(Specification<Prescription> specification, Pageable pageable);

    /**
     * Update an existing prescription identified by its unique public ID.
     *
     * This method updates the details of an existing prescription. It first checks if the prescription
     * exists based on the provided `publicId`. If it exists, the update is applied and the updated
     * prescription is saved to the database. The updated prescription is then returned as a `PrescriptionDto`.
     *
     * @param publicId the public ID of the prescription to update.
     * @param prescriptionDto the DTO containing updated prescription data.
     * @return the updated `PrescriptionDto` after applying changes and saving.
     * @throws RuntimeException if a prescription with the given publicId is not found.
     */
    PrescriptionDto updatePrescription(String publicId, PrescriptionDto prescriptionDto);

    /**
     * Delete a prescription by its unique public ID.
     *
     * This method deletes the prescription with the specified `publicId` from the database.
     * It first checks if the prescription exists; if not, an exception is thrown.
     *
     * @param publicId the public ID of the prescription to delete.
     * @throws RuntimeException if a prescription with the given publicId is not found.
     */
    void deletePrescription(String publicId);

    /**
     * Checks if the nextVisitDate is after the prescriptionDate.
     *
     * @param prescriptionDate the prescription date
     * @param nextVisitDate    the next visit date
     * @return true if nextVisitDate is after prescriptionDate, false otherwise
     */
    boolean isNextVisitDateValid(LocalDate prescriptionDate, LocalDate nextVisitDate);
}
