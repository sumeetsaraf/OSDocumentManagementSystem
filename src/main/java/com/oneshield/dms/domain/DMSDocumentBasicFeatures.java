package com.oneshield.dms.domain;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import com.oneshield.dms.OSDMSAuditingEntityListener;

@MappedSuperclass
@EntityListeners(OSDMSAuditingEntityListener.class)
public class DMSDocumentBasicFeatures extends DMSAuditable {

    @Column(name = "DOCUMENT_FILE_NAME")
    private String fileName;

    public String getFileName() {
	return fileName;
    }

    public void setFileName(String fileName) {
	this.fileName = fileName;
    }
}
