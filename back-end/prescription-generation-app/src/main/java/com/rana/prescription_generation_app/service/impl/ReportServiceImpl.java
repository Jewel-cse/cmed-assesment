package com.rana.prescription_generation_app.service.impl;

import com.rana.prescription_generation_app.dto.PrescriptionCountDto;
import com.rana.prescription_generation_app.exception.classes.CustomValidationException;
import com.rana.prescription_generation_app.repository.PrescriptionRepository;
import com.rana.prescription_generation_app.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final PrescriptionRepository prescriptionRepository;

    String path = "G:\\Jewel\\cmed_assesment\\back-end\\prescription-generation-app\\reports";

    public List<PrescriptionCountDto> getDaywisePrescriptionCount() {
        return prescriptionRepository.findDayWisePrescriptionCount();
    }

    public String exportreports(String reportFormat) throws FileNotFoundException, JRException {
        List<PrescriptionCountDto> prescriptionCountList = prescriptionRepository.findDayWisePrescriptionCount();

        log.debug("prescriptions list ::{} ",prescriptionCountList);

        //load and compile
        File file = ResourceUtils.getFile("classpath:reports/prescriptionCount.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(prescriptionCountList);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("generatedBy", "CMED Health");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        try{
            if (reportFormat.equalsIgnoreCase("html")) {
                JasperExportManager.exportReportToHtmlFile(jasperPrint, path+"\\prescriptionCount.html");
            } else {
                JasperExportManager.exportReportToPdfFile(jasperPrint, path+"\\precriptionCount.pdf");
            }
        } catch (Exception e) {
            log.debug("report export error {}", e.getMessage());
            throw new CustomValidationException(e.getMessage());
        }
        return "Report is generated at path :  " + path;
    }

}
