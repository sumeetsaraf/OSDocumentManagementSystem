package com.oneshield.dms.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oneshield.dms.common.DMSDocumentStatus;
import com.oneshield.dms.domain.DMSDocument;

@Repository
public interface DocumentRepository extends JpaRepository<DMSDocument, Long> {

    DMSDocument findByDmsIdAndDocumentStatusNot(String dmsId, DMSDocumentStatus documentStatus);

}
