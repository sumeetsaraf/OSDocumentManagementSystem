package com.oneshield.dms.domain;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners(OSDMSAuditingEntityListener.class)
public class DMSDocumentBasicFeatures extends DMSAuditable {

    @Column(name = "DOCUMENT_FILE_NAME")
    private String fileName;
    @Column(name = "CONTENT_SIZE_KB")
    private Long contentSize;
    private String contentCode;
    private String contentType;
    private String description;

    public String getFileName() {
	return fileName;
    }

    public void setFileName(String fileName) {
	this.fileName = fileName;
    }

    public Long getContentSize() {
	return contentSize;
    }

    public void setContentSize(Long contentSize) {
	this.contentSize = contentSize;
    }

    public String getContentCode() {
	return contentCode;
    }

    public void setContentCode(String contentCode) {
	this.contentCode = contentCode;
    }

    public String getContentType() {
	return contentType;
    }

    public void setContentType(String contentType) {
	this.contentType = contentType;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

}
