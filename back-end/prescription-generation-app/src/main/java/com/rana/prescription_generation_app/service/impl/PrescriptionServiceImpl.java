package com.rana.prescription_generation_app.service.impl;

import com.rana.prescription_generation_app.dto.PrescriptionDto;
import com.rana.prescription_generation_app.entity.Prescription;
import com.rana.prescription_generation_app.exception.classes.CustomAlreadyExistsException;
import com.rana.prescription_generation_app.exception.classes.CustomNotFoundException;
import com.rana.prescription_generation_app.exception.classes.CustomValidationException;
import com.rana.prescription_generation_app.mapper.PrescriptionMapper;
import com.rana.prescription_generation_app.repository.PrescriptionRepository;
import com.rana.prescription_generation_app.service.PrescriptionService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Service implementation for managing prescriptions.
 */
@Service
@RequiredArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService {

    private static final Logger logger = LoggerFactory.getLogger(PrescriptionServiceImpl.class);

    private final PrescriptionRepository prescriptionRepository;
    private final PrescriptionMapper prescriptionMapper;

    @Override
    public PrescriptionDto createPrescription(PrescriptionDto prescriptionDto) {
        if (prescriptionDto.getPublicId() != null && prescriptionRepository.existsByPublicId(prescriptionDto.getPublicId())) {
            throw new CustomAlreadyExistsException("Prescription already exists");
        }
        if (!isNextVisitDateValid(prescriptionDto.getPrescriptionDate(), prescriptionDto.getNextVisitDate())) {
            throw new CustomValidationException("Next visit date must be in the future");
        }
        Prescription prescription = prescriptionMapper.toPrescription(prescriptionDto);

        Prescription savedPrescription = prescriptionRepository.save(prescription);
        return prescriptionMapper.toPrescriptionDto(savedPrescription);
    }

    @Override
    public PrescriptionDto getPrescriptionById(String publicId) {
        Optional<Prescription> prescription = prescriptionRepository.findByPublicId(publicId);
        if (prescription.isEmpty()) {
            throw new CustomNotFoundException("Prescription not found with id: " + publicId);
        }
        return prescriptionMapper.toPrescriptionDto(prescription.get());
    }

    @Override
    public Page<PrescriptionDto> getAllPrescriptions(Specification<Prescription> specification, Pageable pageable) {
        Page<Prescription> prescriptionsPage = prescriptionRepository.findAll(specification, pageable);
        return prescriptionsPage.map(prescriptionMapper::toPrescriptionDto);
    }

    @Override
    public PrescriptionDto updatePrescription(String publicId, PrescriptionDto prescriptionDto) {
        Optional<Prescription> existingPrescription = prescriptionRepository.findByPublicId(publicId);
        if (existingPrescription.isEmpty()) {
            throw new CustomNotFoundException("Prescription not found with publicId: " + publicId);
        }
        if (!isNextVisitDateValid(prescriptionDto.getPrescriptionDate(), prescriptionDto.getNextVisitDate())) {
            throw new CustomValidationException("Next visit date must be in the future");
        }

        Prescription prescription = existingPrescription.get();
        prescriptionMapper.updatePrescriptionFromDto(prescriptionDto, prescription);

        Prescription updatedPrescription = prescriptionRepository.save(prescription);
        return prescriptionMapper.toPrescriptionDto(updatedPrescription);
    }

    @Override
    public void deletePrescription(String publicId) {
        Optional<Prescription> prescription = prescriptionRepository.findByPublicId(publicId);
        if (prescription.isEmpty()) {
            throw new CustomNotFoundException("Prescription not found with publicId: " + publicId);
        }
        prescriptionRepository.delete(prescription.get());
    }


    public boolean isNextVisitDateValid(@NonNull LocalDate prescriptionDate, LocalDate nextVisitDate) {
        return nextVisitDate == null || nextVisitDate.isAfter(prescriptionDate);
    }

}
