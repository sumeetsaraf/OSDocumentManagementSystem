package com.oneshield.dms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.oneshield.dms.domain.Document;
import com.oneshield.dms.domain.repository.DocumentRepository;

@Service
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW)
public class DocumentServiceImpl implements DocumentService {

	@Autowired
	DocumentRepository documentRepository;

	@Override
	public String getHello() {
		return "Hello from Document Service";
	}

	@Override
	public void saveDocument(Document documentToSave) {
		System.out.println("Document Saved-->"+documentToSave.toString());
		documentRepository.save(documentToSave);
	}

	@Override
	public List<Document> getDocuments() {
		
		return documentRepository.findAll();
	}
	


}
