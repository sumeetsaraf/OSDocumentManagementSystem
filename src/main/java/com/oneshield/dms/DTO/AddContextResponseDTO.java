package com.oneshield.dms.DTO;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.oneshield.dms.common.DMSResponseActionStatus;

@JsonRootName("documentSearchResult")
public class AddContextResponseDTO {

    @JsonProperty("responseDocumentResult")
    private List<AddDocumentResponseDTO> responseResult;
    private DMSResponseActionStatus status;

    public List<AddDocumentResponseDTO> getResponseResult() {
	responseResult.sort((o1, o2)->o2.getCreatedDate().compareTo(o1.getCreatedDate()));
	return responseResult;
    }

    public void setResponseResult(List<AddDocumentResponseDTO> responseResult) {
	this.responseResult = responseResult;
    }

    public DMSResponseActionStatus getResponseStatus() {
	return status;
    }

    public void setResponseStatus(DMSResponseActionStatus status) {
	this.status = status;
    }

    public AddContextResponseDTO() {
	responseResult = new ArrayList<>();
    }

}
