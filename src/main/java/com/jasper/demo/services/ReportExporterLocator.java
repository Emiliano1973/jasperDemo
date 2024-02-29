package com.jasper.demo.services;

import com.jasper.demo.utils.ContentType;

public interface ReportExporterLocator {

    ReportExporter getReportExporter(ContentType contentType);
}
