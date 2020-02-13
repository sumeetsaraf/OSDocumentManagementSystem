package com.oneshield.dms.DTO;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.oneshield.dms.common.DMSResponseActionStatus;

@JsonRootName("documentSearchResult")
public class AddContextResponseDTO extends ResponseDTO {

    @JsonProperty("responseDocumentResult")
    private List<AddDocumentResponseDTO> responseResult;
    private DMSResponseActionStatus status;

    public List<AddDocumentResponseDTO> getResponseResult() {
	return responseResult;
    }

    public void setResponseResult(List<AddDocumentResponseDTO> responseResult) {
	this.responseResult = responseResult;
    }

    public DMSResponseActionStatus getStatus() {
	return status;
    }

    public void setStatus(DMSResponseActionStatus status) {
	this.status = status;
    }

    public AddContextResponseDTO() {
	responseResult = new ArrayList<>();
    }
}
