package com.oneshield.dms.DTO;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("documentContentType")
    private DocumentContentFeaturesDTO contentFeatures;
    private byte[] documentContent;
    @Size(max = 50)
    private String documentCode;
    @Size(max = 100)
    private String renderingTemplate;
    @Size(max = 100)
    private String renderingType;
    private String attachmentType;
    private Long versionId;
    private Long renderingTemplateId;
    private String documentGenerationType="M";
    
    public String getDocumentCode() {
	return documentCode;
    }

    public void setDocumentCode(String documentCode) {
	this.documentCode = documentCode;
    }

    public String getRenderingTemplate() {
	return renderingTemplate;
    }

    public void setRenderingTemplate(String renderingTemplate) {
	this.renderingTemplate = renderingTemplate;
    }

    public String getRenderingType() {
	return renderingType;
    }

    public void setRenderingType(String renderingType) {
	this.renderingType = renderingType;
    }

    public String getAttachmentType() {
	return attachmentType;
    }

    public void setAttachmentType(String attachmentType) {
	this.attachmentType = attachmentType;
    }

    public Long getVersionId() {
	return versionId;
    }

    public void setVersionId(Long versionId) {
	this.versionId = versionId;
    }

    public Long getRenderingTemplateId() {
	return renderingTemplateId;
    }

    public void setRenderingTemplateId(Long renderingTemplateId) {
	this.renderingTemplateId = renderingTemplateId;
    }

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

    public DocumentContentFeaturesDTO getContentFeatures() {
	if (contentFeatures == null) {
	    contentFeatures = new DocumentContentFeaturesDTO();
	}
	return contentFeatures;
    }

    public void setContentFeatures(DocumentContentFeaturesDTO contentFeatures) {
	this.contentFeatures = contentFeatures;
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

    public String getDocumentGenerationType() {
	return documentGenerationType;
    }

    public void setDocumentGenerationType(String documentGenerationType) {
	this.documentGenerationType = documentGenerationType;
    }
}
