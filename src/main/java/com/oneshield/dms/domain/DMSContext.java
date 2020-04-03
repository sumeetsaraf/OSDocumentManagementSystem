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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "DMS_CONTEXT")
public class DMSContext implements Comparable<DMSContext> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DMS_CONTEXT_SEQ")
    @SequenceGenerator(name = "DMS_CONTEXT_SEQ", sequenceName = "DMS_CONTEXT_SEQ")
    private Long id;

    @Column(unique = true)
    private Long contextObjectId;

    private String contextObjectType;

    private Long parentContextObjectId;

    private Long masterContextObjectId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "context", fetch = FetchType.EAGER)
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

    public Long getContextObjectId() {
	return contextObjectId;
    }

    public void setContextObjectId(Long contextObjectId) {
	this.contextObjectId = contextObjectId;
    }

    public String getContextObjectType() {
	return contextObjectType;
    }

    public void setContextObjectType(String contextObjectType) {
	this.contextObjectType = contextObjectType;
    }

    public DMSDocument createNewDocument() {
	DMSDocument document = new DMSDocument();
	document.setContext(this);
	this.getSetOfDocuments().add(document);
	return document;
    }

    public Long getParentContextObjectId() {
	return parentContextObjectId;
    }

    public void setParentContextObjectId(Long parentContextObjectId) {
	this.parentContextObjectId = parentContextObjectId;
    }

    @Override
    public int compareTo(DMSContext o) {
	return this.contextObjectId.compareTo(o.getContextObjectId());
    }

    public Long getMasterContextObjectId() {
	return masterContextObjectId;
    }

    public void setMasterContextObjectId(Long masterContextObjectId) {
	this.masterContextObjectId = masterContextObjectId;
    }

}
