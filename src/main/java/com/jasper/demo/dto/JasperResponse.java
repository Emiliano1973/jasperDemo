package com.jasper.demo.dto;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class JasperResponse {

    private final String fileName;
    private final String contentType;

    private final byte[] response ;

    public JasperResponse(String fileName, String contentType, byte[] response) {
        this.fileName = fileName;
        this.contentType = contentType;
        byte[]  internalRes=new byte[response.length];
        System.arraycopy(response, 0,internalRes, 0, internalRes.length);
        this.response = internalRes;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getContentType() {
        return this.contentType;
    }

    public int documentLength(){
        return this.response.length;
    }
    public InputStream getResponse() {
        return new ByteArrayInputStream(this.response);
    }
}
