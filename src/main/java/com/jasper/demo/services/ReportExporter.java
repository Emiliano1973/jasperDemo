package com.jasper.demo.services;

import net.sf.jasperreports.engine.JasperPrint;

public interface ReportExporter {

    byte[] exportReport(JasperPrint jasperPrint);

}
