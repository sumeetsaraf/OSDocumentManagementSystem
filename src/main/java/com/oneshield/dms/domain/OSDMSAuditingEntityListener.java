package com.oneshield.dms.domain;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

public class OSDMSAuditingEntityListener {
    
    @PrePersist
    @PreRemove
    private void prePersist(DMSAuditable auditableObject) {
	auditableObject.setCreatedDate(new Date());
    }

    @PreUpdate
    private void preUpdate(DMSAuditable auditableObject) {
	auditableObject.setLastModifiedDate(new Date());
    }
}
