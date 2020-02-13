package com.oneshield.dms.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oneshield.dms.domain.DMSContext;

public interface ContextRepository extends JpaRepository<DMSContext, Long>
{
    DMSContext findByAssociateObjectId(Long associateObjectId);
}
