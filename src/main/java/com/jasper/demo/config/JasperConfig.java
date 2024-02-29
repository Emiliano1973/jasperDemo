package com.jasper.demo.config;

import com.jasper.demo.services.ReportExporter;
import com.jasper.demo.services.ReportExporterLocator;
import com.jasper.demo.services.ReportService;
import com.jasper.demo.services.impl.ReportExporterLocatorImpl;
import com.jasper.demo.services.impl.ReportServiceImpl;
import com.jasper.demo.services.impl.exporters.ReportExcelExporterImpl;
import com.jasper.demo.services.impl.exporters.ReportExcelOpenXmlExporterImpl;
import com.jasper.demo.services.impl.exporters.ReportHtmlExporterImpl;
import com.jasper.demo.services.impl.exporters.ReportPdfExporterImpl;
import com.jasper.demo.utils.ContentType;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class JasperConfig {


    @Bean
    public ReportService reportService(DataSource dataSource){
        JasperReport jasperReport=null;
        try(InputStream theWorldStream
                    = getClass().getResourceAsStream("/theworld2.jrxml")) {
             jasperReport
                    = JasperCompileManager.compileReport(theWorldStream);
        } catch (JRException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ReportServiceImpl(jasperReport, reportExporterLocator(), dataSource);
    }


     @Bean
    ReportExporterLocator reportExporterLocator(){
         Map<ContentType, ReportExporter> reportExporterMap=new HashMap<>(4);
         reportExporterMap.put(ContentType.EXCEL, reportExcelExporter());
         reportExporterMap.put(ContentType.EXCEL_OPEN_XML, reportExcelOpenXmlExporter());
         reportExporterMap.put(ContentType.HTML, reportHtmlExporter());
         reportExporterMap.put(ContentType.PDF, reportPdfExporter());
         return  new ReportExporterLocatorImpl(reportExporterMap);
     }

    @Bean( "reportExcelExporter")
    ReportExporter reportExcelExporter(){
        return new ReportExcelExporterImpl();
    }

    @Bean( "reportExcelOpenXmlExporter")
    ReportExporter reportExcelOpenXmlExporter(){
        return new ReportExcelOpenXmlExporterImpl();
    }

    @Bean( "reportHtmlExporter")
    ReportExporter reportHtmlExporter(){
        return new ReportHtmlExporterImpl();
    }

    @Bean( "reportPdfExporter")
    ReportExporter reportPdfExporter(){
        return new ReportPdfExporterImpl();
    }
}
