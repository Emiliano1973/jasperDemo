package com.jasper.demo.services.impl;

import com.jasper.demo.dto.JasperResponse;
import com.jasper.demo.services.ReportExporter;
import com.jasper.demo.services.ReportExporterLocator;
import com.jasper.demo.services.ReportService;
import com.jasper.demo.utils.ContentType;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ReportServiceImpl implements ReportService {

    private final JasperReport jasperReport;
    private final ReportExporterLocator reportExporterLocator;
    private final DataSource dataSource;



    public ReportServiceImpl(final JasperReport jasperReport, final ReportExporterLocator reportExporterLocator,final DataSource dataSource) {
        this.jasperReport = jasperReport;
        this.reportExporterLocator=reportExporterLocator;
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
        } catch (SQLException | JRException e) {
            throw new RuntimeException("Error in report generation :"+e.getMessage(),e);
        }
        return jasperResponse;
    }

    private byte[] getReport(JasperPrint jasperPrint, ContentType contentType)  {
        ReportExporter reportExporter=this.reportExporterLocator.getReportExporter(contentType);
        return reportExporter.exportReport(jasperPrint);
    }

}
