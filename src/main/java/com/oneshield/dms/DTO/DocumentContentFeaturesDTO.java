package com.oneshield.dms.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("documentContentType")
public class DocumentContentFeaturesDTO {

    @JsonProperty("code")
    private String contentCode;

    @JsonProperty("text")
    private String contentType;

    public String getContentCode() {
	return contentCode;
    }

    public void setContentCode(String contentCode) {
	this.contentCode = contentCode;
    }

    public String getContentType() {
	return contentType;
    }

    public void setContentType(String contentType) {
	this.contentType = contentType;
    }

}
