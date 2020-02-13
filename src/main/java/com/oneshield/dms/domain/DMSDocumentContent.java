package com.oneshield.dms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.oneshield.dms.OSDMSAuditingEntityListener;

@Entity
@Table(name = "DMS_DOCUMENT_CONTENT", schema = "BASE_ST")
@EntityListeners(OSDMSAuditingEntityListener.class)
//@JsonRootName(value="DocumentContents")
public class DMSDocumentContent extends DMSAuditable implements Cloneable {

    @Id
    // @Column(name = "DOCUMENT_CONTENT_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DMS_DOCUMENT_CONTENT_SEQ")
    @SequenceGenerator(name = "DMS_DOCUMENT_CONTENT_SEQ", sequenceName = "DMS_DOCUMENT_CONTENT_SEQ")
    private Long id;

    @Lob
    @Column(name = "DOCUMENT_CONTENT")
    private byte[] documentContentLob;

    public DMSDocumentContent() {
	super();
    }

    @Override
    public DMSDocumentContent clone() {
	DMSDocumentContent clone = new DMSDocumentContent();
	clone.setDocumentContent(documentContentLob);
	return clone;
    }

    public Long getDocumentContentId() {
	return id;
    }

    public void setDocumentContentId(Long documentContentId) {
	this.id = documentContentId;
    }

    public byte[] getDocumentContent() {
	return documentContentLob;
    }

    public void setDocumentContent(byte[] documentContent) {
	this.documentContentLob = documentContent;
    }

}
