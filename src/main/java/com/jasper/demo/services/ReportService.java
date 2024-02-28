package com.jasper.demo.services;

import com.jasper.demo.dto.JasperResponse;
import com.jasper.demo.utils.ContentType;

public interface ReportService {

     JasperResponse getJasperResponse(ContentType contentType);
}
