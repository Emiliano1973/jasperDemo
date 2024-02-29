package com.jasper.demo.controller;


import com.jasper.demo.dto.JasperResponse;
import com.jasper.demo.services.ReportService;
import com.jasper.demo.utils.ContentType;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/world")
public class JasperController {

    private final ReportService reportService;

    public JasperController(final ReportService reportService) {
        this.reportService = reportService;
    }


    @GetMapping(value = "/{type}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> getReport(@PathVariable("type") final ContentType contentType) {
        JasperResponse jasperResponse=this.reportService.getJasperResponse(contentType);
        return ResponseEntity.ok().contentLength(jasperResponse.getResponseLength())
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + jasperResponse.getFileName() + "\"")
                .contentType(jasperResponse.getContentType())
                .body(new InputStreamResource(jasperResponse.getResponse()));
    }

}
