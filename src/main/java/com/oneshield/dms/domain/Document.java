package com.oneshield.dms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "DMS_DOCUMENT_TYPE_MAP", schema = "BASE_ST")
public class Document {

	@Id
	@Column(name = "DOCUMENT_ID")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "DMS_DOCUMENT_SEQ")
    @SequenceGenerator(name = "DMS_DOCUMENT_SEQ", sequenceName = "DMS_DOCUMENT_SEQ")
	private Long id;

	@Column(name = "ASSOCIATE_OBJECT_ID")
	private int associateObjectId;

	@Column(name = "ASSOCIATE_OBJECT_TYPE")
	private String associateObjectType;

	/*@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "document")
	private List<DocumentContent> contents = new ArrayList<>();*/



	public Document() {
		super();
	}

	public Document(int associateObjectId, String associateObjectType) {
		this();
		this.associateObjectId = associateObjectId;
		this.associateObjectType = associateObjectType;
	}

	@Override
	public String toString() {
		return "Document [id=" + id + ", associateObjectId=" + associateObjectId + ", associateObjectType="
				+ associateObjectType + "]";
	}

	public int getAssociateObjectId() {
		return associateObjectId;
	}

	public void setAssociateObjectId(int associateObjectId) {
		this.associateObjectId = associateObjectId;
	}

	public String getAssociateObjectType() {
		return associateObjectType;
	}

	public void setAssociateObjectType(String associateObjectType) {
		this.associateObjectType = associateObjectType;
	}

	public Long getId() {
		return id;
	}

}
