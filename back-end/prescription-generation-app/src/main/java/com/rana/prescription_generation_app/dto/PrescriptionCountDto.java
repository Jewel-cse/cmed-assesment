package com.rana.prescription_generation_app.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PrescriptionCountDto {
    private String date;
    private Long totalPrescriptions;
}

