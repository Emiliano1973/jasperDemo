package com.jasper.demo.services.impl.exporters;

import com.jasper.demo.services.ReportExporter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterConfiguration;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ReportHtmlExporterImpl implements ReportExporter {
    @Override
    public byte[] exportReport(JasperPrint jasperPrint) {
        byte[] report = null;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            HtmlExporter exporter = new HtmlExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setConfiguration(new SimpleHtmlExporterConfiguration());
            exporter.setExporterOutput(new SimpleHtmlExporterOutput(outputStream));
            exporter.exportReport();
            report = outputStream.toByteArray();
        } catch (IOException | JRException e) {

        }
        return report;
    }
}
