package com.oneshield.dms.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "DMS_CONTEXT", schema = "BASE_ST")
public class DMSContext {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DMS_CONTEXT_SEQ")
    @SequenceGenerator(name = "DMS_CONTEXT_SEQ", sequenceName = "DMS_CONTEXT_SEQ")
    private Long id;

    @Column(unique = true)
    private Long associateObjectId;

    private String associateObjectType;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "context")
    private Set<DMSDocument> setOfDocuments;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Set<DMSDocument> getSetOfDocuments() {
	if (setOfDocuments == null) {
	    setOfDocuments = new HashSet<>();
	}
	return setOfDocuments;
    }

    public void setLisOfDocuments(Set<DMSDocument> lisOfDocuments) {
	this.setOfDocuments = lisOfDocuments;
    }

    public Long getAssociateObjectId() {
	return associateObjectId;
    }

    public void setAssociateObjectId(Long associateObjectId) {
	this.associateObjectId = associateObjectId;
    }

    public String getAssociateObjectType() {
	return associateObjectType;
    }

    public void setAssociateObjectType(String associateObjectType) {
	this.associateObjectType = associateObjectType;
    }

    public DMSDocument createNewDocument() {
	DMSDocument document = new DMSDocument();
	document.setContext(this);
	this.getSetOfDocuments().add(document);
	return document;
    }

}
