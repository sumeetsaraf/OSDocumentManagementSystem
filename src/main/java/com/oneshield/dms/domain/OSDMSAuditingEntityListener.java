package com.oneshield.dms.domain;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

public class OSDMSAuditingEntityListener
{
    @PrePersist
    @PreUpdate
    @PreRemove
    private void beforeAnyOperation(Object object)
    {
        DMSAuditable objectToAddAudit = (DMSAuditable) object;
        objectToAddAudit.setCreatedDate(new Date());
        objectToAddAudit.setLastModifiedDate(new Date());
    }
}
