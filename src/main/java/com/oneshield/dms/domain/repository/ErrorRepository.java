package com.oneshield.dms.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oneshield.dms.domain.DMSErrors;

public interface ErrorRepository extends JpaRepository<DMSErrors, Long>{

}
