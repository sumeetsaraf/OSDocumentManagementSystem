package com.oneshield.dms.domain.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oneshield.dms.domain.DMSContext;

public interface ContextRepository extends JpaRepository<DMSContext, Long> {

    Set<DMSContext> findByContextObjectIdOrParentContextObjectId(Long associateObjectId, Long associateParent);

    Set<DMSContext> findByParentContextObjectId(Long parentContextObjectId);

    Set<DMSContext> findByMasterContextObjectId(Long masterContextObjectId);

    DMSContext findByContextObjectId(Long associateObjectId);
}
