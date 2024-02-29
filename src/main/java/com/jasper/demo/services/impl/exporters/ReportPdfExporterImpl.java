package com.jasper.demo.services.impl.exporters;

import com.jasper.demo.services.ReportExporter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.stereotype.Component;

public class ReportPdfExporterImpl implements ReportExporter {
    @Override
    public byte[] exportReport(JasperPrint jasperPrint) {
        byte[] report=null;
        try {
          report= JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (JRException e) {
            throw new RuntimeException("Error to export report :"+e.getMessage(), e);
        }
        return report;
    }
}
