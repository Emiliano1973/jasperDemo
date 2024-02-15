package com.jasper.demo.config;

import com.jasper.demo.services.ReportService;
import com.jasper.demo.services.impl.ReportServiceImpl;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;

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
        return new ReportServiceImpl(jasperReport, dataSource);

    }
}
