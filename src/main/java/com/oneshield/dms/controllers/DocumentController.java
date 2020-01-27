package com.oneshield.dms.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oneshield.dms.domain.Document;
import com.oneshield.dms.domain.DocumentContent;
import com.oneshield.dms.domain.repository.DocumentRepository;
import com.oneshield.dms.service.DocumentService;

@RestController
@RequestMapping("/api/document-management")
public class DocumentController {

	@Autowired
	DocumentService documentService;


	@GetMapping("/document")
	public ResponseEntity sayHello() {
		Document a = new Document(12, "FirstObject");
			return ResponseEntity.ok(a);
	}

	@GetMapping("/getDocuments")
	public ResponseEntity<List<Document>> getFirstDocument() {
	/*	Document a = new Document(12, "FirstObject");
		DocumentContent dc1 = new DocumentContent(a, "dc1-abc");
		DocumentContent dc2 = new DocumentContent(a, "dc2-xyz");
		a.getContents().add(dc1);
		a.getContents().add(dc2);*/
		//documentService.saveDocument(a);
		return ResponseEntity.ok().body(documentService.getDocuments());
	}
	
	@PostMapping("/documents")
    public ResponseEntity createDocument(@Valid @RequestBody Document documentToSave) {
		documentService.saveDocument(documentToSave);
		return ResponseEntity.ok("Success");
    }
}
