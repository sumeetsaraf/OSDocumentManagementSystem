package com.oneshield.dms.DTO;

import com.oneshield.dms.common.DMSResponseActionStatus;

public class AddDocumentResponseDTO extends ResponseDTO {

    private String dmsId;
    private DMSResponseActionStatus responseStatus;
    
    public String getDmsId() {
	return dmsId;
    }

    public void setDmsId(String dmsId) {
	this.dmsId = dmsId;
    }

    public DMSResponseActionStatus getResponseStatus() {
	return responseStatus;
    }

    public void setResponseStatus(DMSResponseActionStatus responseStatus) {
	this.responseStatus = responseStatus;
    }

}
