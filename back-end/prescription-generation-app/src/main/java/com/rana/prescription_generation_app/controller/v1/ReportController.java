package com.rana.prescription_generation_app.controller.v1;

import com.rana.prescription_generation_app.dto.PrescriptionCountDto;
import com.rana.prescription_generation_app.service.ReportService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.List;

import static com.rana.prescription_generation_app.constants.API_V1.REPORT_URL;

@RestController
@RequestMapping(REPORT_URL)
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping()
    public ResponseEntity<List<PrescriptionCountDto>> getReport() {
        return ResponseEntity.ok(reportService.getDaywisePrescriptionCount());
    }

    @GetMapping("/day-wise-count")
    public ResponseEntity<?> generateDaywisePrescriptionCount(@RequestParam(required = false) String format) throws JRException, FileNotFoundException {
        return ResponseEntity.ok(reportService.exportreports(format));
    }
}
