package com.jasper.demo.services.impl;

import com.jasper.demo.services.ReportExporter;
import com.jasper.demo.services.ReportExporterLocator;
import com.jasper.demo.utils.ContentType;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ReportExporterLocatorImpl implements ReportExporterLocator {

    private final Map<ContentType, ReportExporter> reportExporterMap;

    public ReportExporterLocatorImpl(final Map<ContentType, ReportExporter> reportExporterMap
                                     ) {
        this.reportExporterMap= Map.copyOf(reportExporterMap);
    }

    @Override
    public ReportExporter getReportExporter(ContentType contentType) {
        return  this.reportExporterMap.get(contentType);
    }
}
