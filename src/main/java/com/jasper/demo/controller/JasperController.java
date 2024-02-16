package com.jasper.demo.controller;


import com.jasper.demo.dto.JasperResponse;
import com.jasper.demo.services.ReportService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/world")
public class JasperController {

    private final ReportService reportService;

    public JasperController(ReportService reportService) {
        this.reportService = reportService;
    }


    @GetMapping
    @ResponseBody
    public ResponseEntity<InputStreamResource> getReport() {
        JasperResponse jasperResponse=this.reportService.getJasperResponse();
        return ResponseEntity.ok().contentLength(jasperResponse.getResponseLength())
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + jasperResponse.getFileName() + "\"")
                .contentType(MediaType.parseMediaType(jasperResponse.getContentType()))
                .body(new InputStreamResource(jasperResponse.getResponse()));
    }


}
