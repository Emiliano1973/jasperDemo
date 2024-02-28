package com.jasper.demo.services.impl;

import com.jasper.demo.dto.JasperResponse;
import com.jasper.demo.services.ReportService;
import com.jasper.demo.utils.ContentType;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterConfiguration;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class ReportServiceImpl implements ReportService {

    private final JasperReport jasperReport;
    private final DataSource dataSource;

    public ReportServiceImpl(final JasperReport jasperReport,final DataSource dataSource) {
        this.jasperReport = jasperReport;
        this.dataSource = dataSource;
    }

    @Override
    public JasperResponse getJasperResponse(final ContentType contentType) {
        JasperResponse jasperResponse = null;
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport, null, connection);
            byte[] document = getReport(jasperPrint, contentType);
            jasperResponse = new JasperResponse("world."+contentType.getExtension(), contentType.getContentType(),
                    document);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (JRException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return jasperResponse;
    }


    private byte[] getReport(JasperPrint jasperPrint, ContentType contentType) throws JRException, IOException {
        return switch (contentType) {
            case PDF -> JasperExportManager.exportReportToPdf(jasperPrint);
            case EXCEL -> getExcelReport(jasperPrint);
            case EXCEL_OPEN_XML ->  getExcelOpenFormatReport(jasperPrint);
            case HTML -> getHtmlReport(jasperPrint);
            default -> null;
        };
    }


    private byte[] getExcelReport(final JasperPrint jasperPrint) throws IOException, JRException {
        byte[] report = null;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setConfiguration(new SimpleXlsExporterConfiguration());
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
            exporter.exportReport();
            report = outputStream.toByteArray();
        }
        return report;
    }

    private byte[] getExcelOpenFormatReport(final JasperPrint jasperPrint) throws IOException, JRException {
        byte[] report = null;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            SimpleXlsxReportConfiguration reportConfigXLS = new SimpleXlsxReportConfiguration();
            reportConfigXLS.setRemoveEmptySpaceBetweenRows(true);
            exporter.setConfiguration(reportConfigXLS);
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
            exporter.exportReport();
            report = outputStream.toByteArray();
        }
        return report;
    }

    private byte[] getHtmlReport(final JasperPrint jasperPrint) throws IOException, JRException {
        byte[] report = null;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            HtmlExporter exporter = new HtmlExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setConfiguration(new SimpleHtmlExporterConfiguration());
            exporter.setExporterOutput(new SimpleHtmlExporterOutput(outputStream));
            exporter.exportReport();
            report = outputStream.toByteArray();
        }
        return report;
    }
}
