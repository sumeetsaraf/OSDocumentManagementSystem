package com.oneshield.dms.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
@JsonRootName("context")
public class AddContextDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("associateObjectId")
    @NotNull
    private Long associateObject;

    @NotNull
    private String associateObjectType;

    @JsonProperty("documentContent")
    @NotNull
    private List<DocumentContentDTO> listOfDocuments;

    private String os_st_id;

    private String os_id;

    public Long getAssociateObjectId() {
	return associateObject;
    }

    public void setAssociateObjectId(Long associateObjectId) {
	this.associateObject = associateObjectId;
    }

    public String getAssociateObjectType() {
	return associateObjectType;
    }

    public void setAssociateObjectType(String associateObjectType) {
	this.associateObjectType = associateObjectType;
    }

    public List<DocumentContentDTO> getDocumentContent() {
	if (listOfDocuments == null) {
	    listOfDocuments = new ArrayList<>();
	}
	return listOfDocuments;
    }

    public void setDocumentContent(List<DocumentContentDTO> documentcontent) {
	this.listOfDocuments = documentcontent;
    }

    public String getOs_st_id() {
	return os_st_id;
    }

    public void setOs_st_id(String os_st_id) {
	this.os_st_id = os_st_id;
    }

    public String getOs_id() {
	return os_id;
    }

    public void setOs_id(String os_id) {
	this.os_id = os_id;
    }

}
