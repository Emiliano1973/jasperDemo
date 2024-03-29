package com.jasper.demo.dto;

import org.springframework.http.MediaType;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public final class JasperResponse {

    private final String fileName;
    private final MediaType contentType;
    private final int responseLength;
    private final byte[] response ;

    public JasperResponse(final String fileName, final MediaType contentType, final byte[] response) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.responseLength=response.length;
        byte[]  internalRes=new byte[this.responseLength];
        System.arraycopy(response, 0,internalRes, 0, this.responseLength);
        this.response = internalRes;
    }

    public String getFileName() {
        return this.fileName;
    }

    public MediaType getContentType() {
        return this.contentType;
    }

    public int getResponseLength(){
        return this.responseLength;
    }
    public InputStream getResponse() {
        return new ByteArrayInputStream(this.response);
    }
}
