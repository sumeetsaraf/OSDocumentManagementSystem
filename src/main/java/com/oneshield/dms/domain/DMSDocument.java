package com.oneshield.dms.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.oneshield.dms.common.DMSDocumentStatus;
import com.oneshield.dms.common.DMSHelper;

@Entity
@Table(name = "DMS_DOCUMENT")
public class DMSDocument extends DMSDocumentBasicFeatures implements Cloneable, Comparable<DMSDocument> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DMS_DOCUMENT_SEQ")
    @SequenceGenerator(name = "DMS_DOCUMENT_SEQ", sequenceName = "DMS_DOCUMENT_SEQ", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "DMS_CONTEXT_ID", nullable = false)
    private DMSContext context;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCUMENT_CONTENT_ID", referencedColumnName = "id")
    private DMSDocumentContent documentContent;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "document")
    private Set<DMSDocumentHistory> historyOfDocument;

    private String dmsId;

    @Column(unique = true)
    private String externalDmsId;

    private DMSDocumentStatus documentStatus;

    private String documentCode;
    private String renderingTemplate;
    private String renderingType;
    private String attachmentType;
    private Long versionId;
    private Long renderingTemplateId;
    
    @Column(columnDefinition = "char default 'M'",nullable = false)
    private String documentGenerationType;

    @PrePersist
    public void beforePersist() {
	if (dmsId == null) {
	    this.setDmsId(DMSHelper.getEscapaedTimeForCurrentTimestamp());
	}
	this.updateDocumentSizeBasedOnContent();
	if (this.getExternalDmsId() == null) {
	    this.setExternalDmsId(this.getDmsId());
	}
    }

    @PreUpdate
    public void beforeUpdate() {
	this.updateDocumentSizeBasedOnContent();
    }

    public DMSDocumentContent getDocumentContent() {
	return documentContent;
    }

    public void setDocumentContent(DMSDocumentContent documentContent) {
	this.documentContent = documentContent;
    }

    public DMSDocument() {
	super();
    }

    public Long getDocumentId() {
	return getId();
    }

    public DMSDocument(DMSDocumentContent documentContent) {
	super();
	this.documentContent = documentContent;
    }

    @Override
    public DMSDocument clone() {
	return new DMSDocument(this.getDocumentContent().clone());
    }

    public Long getId() {
	return id;
    }

    public DMSContext getContext() {
	return context;
    }

    public void setContext(DMSContext context) {
	this.context = context;
    }

    public String getDmsId() {
	return dmsId;
    }

    public Set<DMSDocumentHistory> getHistoryOfDocument() {
	if (historyOfDocument == null) {
	    historyOfDocument = new HashSet<>();
	}
	return historyOfDocument;
    }

    public DMSDocumentStatus getDocumentStatus() {
	return documentStatus;
    }

    public void setDocumentStatus(DMSDocumentStatus documentStatus) {
	this.documentStatus = documentStatus;
    }

    private void updateDocumentSizeBasedOnContent() {
	if (documentContent != null && documentContent.getDocumentContent() != null) {
	    this.setContentSize(documentContent.getDocumentContent().length / 1024L);
	}
    }

    @Override
    public int compareTo(DMSDocument o) {
	return this.id.compareTo(o.getId());
    }

    public String getExternalDmsId() {
	return externalDmsId;
    }

    public void setExternalDmsId(String externalDmsId) {
	this.externalDmsId = externalDmsId;
    }

    public void setDmsId(String dmsId) {
	this.dmsId = dmsId;
    }

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

    public String getDocumentGenerationType() {
	return documentGenerationType;
    }

    public void setDocumentGenerationType(String documentGenerationType) {
	this.documentGenerationType = documentGenerationType;
    }

}
