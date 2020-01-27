package com.oneshield.dms.service;

import java.util.List;

import com.oneshield.dms.domain.Document;

public interface DocumentService {

	String getHello();
	
	public void saveDocument(Document documentToSave);
	
	public List<Document> getDocuments();
	
	public Document getDocumentbyID(Long Id);
}
