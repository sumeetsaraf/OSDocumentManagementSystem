package com.oneshield.dms.DTO;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.oneshield.dms.validations.UniqueExternalDmsId;

@JsonRootName("context")
@UniqueExternalDmsId
public class AddContextDTO {

    @NotNull
    @Min(0)
    @Max(9223372036854775806L)
    private Long contextObjectId;

    @NotNull
    private String contextObjectType;
   
    @Min(0)
    @Max(9223372036854775806L)
    private Long parentContextObjectId;

    @Min(0)
    @Max(9223372036854775806L)
    private Long masterContextObjectId;

    @JsonProperty("documentContent")
    @NotNull
    private List<DocumentContentDTO> listOfDocuments;

    private String os_st_id;

    private String os_id;

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

    public List<DocumentContentDTO> getListOfDocuments() {
	if (listOfDocuments == null) {
	    listOfDocuments = new ArrayList<>();
	}
	return listOfDocuments;
    }

    public void setListOfDocuments(List<DocumentContentDTO> documentcontent) {
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

    public Long getParentContextObjectId() {
	return parentContextObjectId;
    }

    public void setParentContextObjectId(Long parentContextObjectId) {
	this.parentContextObjectId = parentContextObjectId;
    }

    public Long getMasterContextObjectId() {
	return masterContextObjectId;
    }

    public void setMasterContextObjectId(Long masterContextObjectId) {
	this.masterContextObjectId = masterContextObjectId;
    }

}
