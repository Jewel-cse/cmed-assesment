package com.rana.prescription_generation_app.controller.v1;

import com.rana.prescription_generation_app.dto.PrescriptionDto;
import com.rana.prescription_generation_app.entity.Prescription;
import com.rana.prescription_generation_app.enums.Gender;
import com.rana.prescription_generation_app.service.PrescriptionService;
import com.rana.prescription_generation_app.specification.PrescriptionSpecification;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static com.rana.prescription_generation_app.constants.API_V1.PRESCRIPTION_URL;

/**
 * REST controller for managing {@link com.rana.prescription_generation_app.entity.Prescription} entities.
 *
 * <p>This controller provides endpoints to:
 * <ul>
 *   <li>Create a new Prescription</li>
 *   <li>Retrieve a list of Prescriptions with filtering options</li>
 *   <li>Retrieve, update, and delete a specific Prescription</li>
 * </ul>
 * </p>
 *
 * @author Md Jewel Rana
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping(PRESCRIPTION_URL)
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @Autowired
    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    /**
     * Create a new prescription.
     *
     * @param prescriptionDto the DTO containing data for the new prescription.
     * @return the ResponseEntity with status 201 (Created) and with body the new prescriptionDto.
     */
    @PostMapping
    public ResponseEntity<PrescriptionDto> createPrescription(@Valid @RequestBody PrescriptionDto prescriptionDto) {
        PrescriptionDto createdPrescription = prescriptionService.createPrescription(prescriptionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPrescription);
    }

    /**
     * Get a list of prescriptions with optional filters.
     *
     * @param page             page number (default: 0)
     * @param size             number of items per page (default: 10)
     * @param sortBy           field to sort by (default: 'id')
     * @param orderBy          sort order (default: 'asc')
     * @param patientName      optional patient name filter
     * @param prescriptionDate optional prescription date filter
     * @return the ResponseEntity with the list of prescriptions in body.
     */
    @GetMapping
    public ResponseEntity<Page<PrescriptionDto>> getAllPrescriptions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "dsc") String orderBy,
            @RequestParam(required = false) String patientName,
            @RequestParam(required = false) Gender gender,
            @RequestParam(required = false) String diagnosis,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate prescriptionDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate prescriptionFromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate prescriptionToDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate nextVisitDate
    ) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                orderBy.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending()
        );

        Specification<Prescription> specification = PrescriptionSpecification.combine(
                PrescriptionSpecification.hasPatientName(patientName),
                PrescriptionSpecification.hasPatientGender(gender),
                PrescriptionSpecification.prescribedOn(prescriptionDate),
                PrescriptionSpecification.hasAgeRange(minAge, maxAge),
                PrescriptionSpecification.hasDiagnosis(diagnosis),
                PrescriptionSpecification.hasNextVisitDate(nextVisitDate)
        );

        Page<PrescriptionDto> prescriptions = prescriptionService.getAllPrescriptions(specification, pageable);
        return ResponseEntity.ok(prescriptions);
    }

    /**
     * Retrieves a {@link Prescription} entity by its ID.
     *
     * @param publicId the ID of the entity to retrieve.
     * @return the retrieved {@link PrescriptionDto}.
     */
    @GetMapping("/{publicId}")
    public ResponseEntity<PrescriptionDto> getPrescriptionById(@PathVariable String publicId) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionById(publicId));
    }

    /**
     * Updates an existing {@link Prescription} entity.
     *
     * @param publicId        the ID of the entity to update.
     * @param prescriptionDto the DTO containing updated data.
     * @return the updated {@link PrescriptionDto}.
     */
    @PutMapping("/{publicId}")
    public ResponseEntity<PrescriptionDto> updatePrescription(@PathVariable @NonNull String publicId, @RequestBody PrescriptionDto prescriptionDto) {
        PrescriptionDto updatedPrescription = prescriptionService.updatePrescription(publicId, prescriptionDto);
        return ResponseEntity.ok(updatedPrescription);
    }

    /**
     * Deletes a {@link Prescription} entity.
     *
     * @param publicId the ID of the entity to delete.
     * @return a ResponseEntity indicating success or failure of the operation.
     */
    @DeleteMapping("/{publicId}")
    public ResponseEntity<Void> deletePrescription(@PathVariable @NonNull String publicId) {
        prescriptionService.deletePrescription(publicId);
        return ResponseEntity.noContent().build();
        /*try {
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }*/
    }
}

