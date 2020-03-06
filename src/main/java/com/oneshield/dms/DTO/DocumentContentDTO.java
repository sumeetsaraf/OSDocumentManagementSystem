package com.oneshield.dms.DTO;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.oneshield.dms.common.DMSResponseActionStatus;

@JsonRootName("documentContent")
public class DocumentContentDTO {

    private DMSResponseActionStatus documentStatus;
    private String dmsId;
    private String externalDmsId;
    private String fileName;
    private String documentDescription;
    private Long contentSize;
    private DocumentContentTypeDTO documentContentType;
    @NotNull
    private byte[] documentContent;

    public DMSResponseActionStatus getDocumentStatus() {
	return documentStatus;
    }

    public void setDocumentStatus(DMSResponseActionStatus responseStatus) {
	this.documentStatus = responseStatus;
    }

    public String getDmsId() {
	return dmsId;
    }

    @JsonIgnore
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

    public String getDocumentDescription() {
	return documentDescription;
    }

    public void setDocumentDescription(String documentDescription) {
	this.documentDescription = documentDescription;
    }

    public DocumentContentTypeDTO getDocumentContentType() {
	return documentContentType;
    }

    public void setDocumentContentType(DocumentContentTypeDTO documentContentType) {
	this.documentContentType = documentContentType;
    }

    public Long getContentSize() {
	return contentSize;
    }

    public void setContentSize(Long contentSize) {
	this.contentSize = contentSize;
    }

    public String getExternalDmsId() {
	return externalDmsId;
    }

    public void setExternalDmsId(String externalDmsId) {
	this.externalDmsId = externalDmsId;
    }
}
