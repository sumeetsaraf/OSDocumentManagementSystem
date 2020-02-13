package com.oneshield.dms.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.uuid.Generators;

@Entity
@Table(name = "DMS_DOCUMENT", schema = "BASE_ST")
public class DMSDocument extends DMSDocumentBasicFeatures implements Cloneable {

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

    @PrePersist
    public void beforePersist() {
	if (dmsId == null) {
	    dmsId = Generators.timeBasedGenerator().generate().toString();
	}
    }

    public String getDmsId() {
	return dmsId;
    }

    public Set<DMSDocumentHistory> getHistoryOfDocument() {
	if(historyOfDocument==null) {
	    historyOfDocument = new HashSet<>();
	}
	return historyOfDocument;
    }

}
