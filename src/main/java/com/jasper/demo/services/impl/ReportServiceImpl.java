package com.jasper.demo.services.impl;

import com.jasper.demo.dto.JasperResponse;
import com.jasper.demo.services.ReportService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import org.springframework.http.MediaType;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class ReportServiceImpl implements ReportService {

    private final JasperReport jasperReport;
    private final DataSource dataSource;

    public ReportServiceImpl(JasperReport jasperReport, DataSource dataSource) {
        this.jasperReport = jasperReport;
        this.dataSource=dataSource;
    }

    @Override
    public JasperResponse getJasperResponse()  {
        JasperResponse jasperResponse=null;
        try (Connection connection=dataSource.getConnection()){
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport, null,connection);
         byte[] document=JasperExportManager.exportReportToPdf(jasperPrint); ;
         jasperResponse=new JasperResponse("world.pdf", MediaType.APPLICATION_PDF_VALUE, document);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
        return  jasperResponse;
    }
}
