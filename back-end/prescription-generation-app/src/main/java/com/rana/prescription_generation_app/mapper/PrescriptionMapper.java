package com.rana.prescription_generation_app.mapper;

import com.rana.prescription_generation_app.dto.PrescriptionDto;
import com.rana.prescription_generation_app.entity.Prescription;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface for converting between {@link Prescription} and {@link PrescriptionDto}.
 *
 * <p>Uses MapStruct to generate the mapping implementation.</p>
 *
 * @author Md Jewel Rana
 * @version 1.0
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface PrescriptionMapper {
    PrescriptionMapper INSTANCE = Mappers.getMapper(PrescriptionMapper.class);

    /**
     * Converts a Prescription entity to a PrescriptionDto.
     *
     * @param prescription the Prescription entity to convert.
     * @return the converted PrescriptionDto.
     */
    PrescriptionDto toPrescriptionDto(Prescription prescription);

    /**
     * Converts a PrescriptionDto to a Prescription entity.
     *
     * @param prescriptionDto the PrescriptionDto to convert.
     * @return the converted Prescription entity.
     */
    Prescription toPrescription(PrescriptionDto prescriptionDto);

    /**
     * Updates an existing Prescription entity with values from a PrescriptionDto.
     *
     * @param prescriptionDto the PrescriptionDto containing updated values.
     * @param prescription the Prescription entity to update.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePrescriptionFromDto(PrescriptionDto prescriptionDto, @MappingTarget Prescription prescription);
}
