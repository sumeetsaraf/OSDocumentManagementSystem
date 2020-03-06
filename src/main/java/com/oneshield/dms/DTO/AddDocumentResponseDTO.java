package com.oneshield.dms.DTO;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.oneshield.dms.common.DMSResponseActionStatus;

public class AddDocumentResponseDTO {

    private String dmsId;
    private String externalDmsId;
    private DMSResponseActionStatus documentStatus;
    private Date createdDate;
    private Date lastModifiedDate;
    private String fileName;
    @JsonProperty("documentContentType")
    private String contentCode;
    private String contentType;
    @JsonProperty("documentDescription")
    private String description;
    private Long contentSize;
    @JsonProperty("contextObjectId")
    private Long contextContextObjectId;
    
    @JsonProperty("contextObjectType")
    private String contextContextObjectType;
    
    @JsonProperty("parentContextObjectId")
    private Long contextParentContextObjectId;
    
    @JsonProperty("masterContextObjectId")
    private Long contextMasterContextObjectId;

    @JsonInclude(Include.NON_NULL)
    private byte[] documentContent;

    public String getDmsId() {
	return dmsId;
    }

    public void setDmsId(String dmsId) {
	this.dmsId = dmsId;
    }

    public DMSResponseActionStatus getDocumentStatus() {
	return documentStatus;
    }

    public void setDocumentStatus(DMSResponseActionStatus responseStatus) {
	this.documentStatus = responseStatus;
    }

    public Date getCreatedDate() {
	return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
	this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
	return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
	this.lastModifiedDate = lastModifiedDate;
    }

    public String getFileName() {
	return fileName;
    }

    public void setFileName(String fileName) {
	this.fileName = fileName;
    }

    public String getContentCode() {
	return contentCode;
    }

    public void setContentCode(String contentCode) {
	this.contentCode = contentCode;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public Long getContentSize() {
	return contentSize;
    }

    public void setContentSize(Long contentSize) {
	this.contentSize = contentSize;
    }

    public Long getContextContextObjectId() {
	return contextContextObjectId;
    }

    public void setContextContextObjectId(Long contextContextObjectId) {
	this.contextContextObjectId = contextContextObjectId;
    }

    public String getContextContextObjectType() {
	return contextContextObjectType;
    }

    public void setContextContextObjectType(String contextContextObjectType) {
	this.contextContextObjectType = contextContextObjectType;
    }

    public byte[] getDocumentContent() {
	return documentContent;
    }

    public void setDocumentContent(byte[] documentContent) {
	this.documentContent = documentContent;
    }

    public String getExternalDmsId() {
	if (externalDmsId == null || externalDmsId.isEmpty()) {
	    externalDmsId = dmsId;
	}
	return externalDmsId;
    }

    public void setExternalDmsId(String externalDmsId) {
	this.externalDmsId = externalDmsId;
    }

    public String getContentType() {
	return contentType;
    }

    public void setContentType(String contentType) {
	this.contentType = contentType;
    }

    public static AddDocumentResponseDTO returnEmpty() {
	AddDocumentResponseDTO a = new AddDocumentResponseDTO();
	a.setDocumentStatus(DMSResponseActionStatus.EMPTY);
	a.setDescription("No documents found!");
	return a;
    }

    public Long getContextParentContextObjectId() {
	return contextParentContextObjectId;
    }

    public void setContextParentContextObjectId(Long contextParentContextObjectId) {
	this.contextParentContextObjectId = contextParentContextObjectId;
    }

    public Long getContextMasterContextObjectId() {
	return contextMasterContextObjectId;
    }

    public void setContextMasterContextObjectId(Long contextMasterContextObjectId) {
	this.contextMasterContextObjectId = contextMasterContextObjectId;
    }
}
