package com.oneshield.dms.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oneshield.dms.domain.DMSDocumentHistory;

@Repository
public interface DocumentHistoryRepository extends JpaRepository<DMSDocumentHistory, Long> {

}
