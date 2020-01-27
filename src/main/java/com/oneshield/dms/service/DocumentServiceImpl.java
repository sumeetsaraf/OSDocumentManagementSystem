package com.oneshield.dms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
		
		
		List<Document> documentList = documentRepository.findAll();
		System.out.println("Document size-->"+documentList.size());

        if(documentList.size() > 0) {
            return documentList;
        } else {
            return new ArrayList<Document>();
        }
		
	}
	
	@Override
	public Document getDocumentbyID(Long Id) {
		
		
		/*Optional<Document> document = documentRepository.findOne(Id);
        
        if(document.isPresent()) {
            return document.get();
        } else {
            //throw new RecordNotFoundException("No employee record exist for given id");
        	System.err.println("Exception - No record found for that Id");
        }*/
		System.out.println("getting document by ID "+Id);
		
		return documentRepository.findOne(Id);
		
	}
	


}
