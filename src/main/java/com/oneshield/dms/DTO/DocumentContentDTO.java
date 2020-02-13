package com.oneshield.dms.DTO;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.oneshield.dms.common.DMSResponseActionStatus;
@JsonRootName("documentContent")
public class DocumentContentDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private DMSResponseActionStatus responseStatus;
    private String dmsId;
    private String fileName;

    private byte[] documentContent;

    public DMSResponseActionStatus getResponseStatus() {
	return responseStatus;
    }

    public void setResponseStatus(DMSResponseActionStatus responseStatus) {
	this.responseStatus = responseStatus;
    }

    public String getDmsId() {
	return dmsId;
    }

    public void setDmsId(String dmsId) {
	this.dmsId = dmsId;
    }

    public String getFileName() {
	return fileName;
    }

    public void setFileName(String fileName) {
	this.fileName = fileName;
    }

    public byte[] getDocumentContent() {
	return documentContent;
    }

    public void setDocumentContent(byte[] documentContent) {
	this.documentContent = documentContent;
    }
}
