package com.oneshield.dms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="DMD_DOCUMENT_CONTENT")
public class DocumentContent {
	
	@Id
	@Column(name = "DOCUMENT_CONTENT_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Document document;

	@Column(name = "DOCUMENT_FILE_NAME")
	private String fileName;

	public String getFileName() {
		return fileName;
	}
	public Long getDocumentID() {
		return DocumentID;
	}

	public void setDocumentID(Long documentID) {
		DocumentID = documentID;
	}
	@Column(name = "DOCUMENT_ID")
	private Long DocumentID;

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public DocumentContent(Document document, String fileName) {
		this();
		//this.document = document;
		this.fileName = fileName;
	}

	public DocumentContent() {
		super();
	}
	
	
	
}
