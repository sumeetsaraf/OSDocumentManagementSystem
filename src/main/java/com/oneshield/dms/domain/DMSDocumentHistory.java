package com.oneshield.dms.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "DMS_DOCUMENT_HISTORY", schema = "BASE_ST")

public class DMSDocumentHistory extends DMSDocumentBasicFeatures implements Comparable<DMSDocumentHistory> {

    @Id
    @Column(name = "DOCUMENT_HIST_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long documentHistoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_Id", nullable = false)
    private DMSDocument document;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "document_Content_id", referencedColumnName = "id")
    private DMSDocumentContent documentContent;

    @Override
    public int compareTo(DMSDocumentHistory o) {
	return this.documentHistoryId.compareTo(o.documentHistoryId);
    }

    public DMSDocumentContent getDocumentContent() {
	return documentContent;
    }

    public void setDocumentContent(DMSDocumentContent documentContent) {
	this.documentContent = documentContent;
    }

    public DMSDocument getDocument() {
	return document;
    }

    public void setDocument(DMSDocument document) {
	this.document = document;
    }

}
