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
    @SequenceGenerator(name = "DMS_DOCUMENT_SEQ", sequenceName = "DMS_DOCUMENT_SEQ")
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

    @PrePersist
    public void beforePersist() {
	if (dmsId == null) {
	    this.setDmsId(DMSHelper.getEscapaedTimeForCurrentTimestamp());
	}
	this.updateDocumentSizeBasedOnContent();
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

    public void setDocumentId(Long documentId) {
	this.setId(documentId);
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

    public void setId(Long id) {
	this.id = id;
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

}
