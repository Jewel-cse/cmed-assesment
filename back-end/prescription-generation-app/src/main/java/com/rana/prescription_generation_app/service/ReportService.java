package com.rana.prescription_generation_app.service;

import com.rana.prescription_generation_app.dto.PrescriptionCountDto;
import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.util.List;

public interface ReportService {
    List<PrescriptionCountDto> getDaywisePrescriptionCount();

    String exportreports(String reportFormat) throws JRException, FileNotFoundException;
}
