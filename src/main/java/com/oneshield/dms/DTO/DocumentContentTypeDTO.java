package com.oneshield.dms.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DocumentContentTypeDTO {

    @JsonProperty("code")
    private String code;

    @JsonProperty("text")
    private String type;

    public String getCode() {
	return code;
    }

    public void setCode(String code) {
	this.code = code;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

}
