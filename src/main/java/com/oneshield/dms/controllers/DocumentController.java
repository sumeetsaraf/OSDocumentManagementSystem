package com.oneshield.dms.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oneshield.dms.DTO.AddContextDTO;
import com.oneshield.dms.DTO.AddContextResponseDTO;
import com.oneshield.dms.DTO.DocumentContentDTO;
import com.oneshield.dms.common.DMSResponseActionStatus;
import com.oneshield.dms.domain.DMSDocument;
import com.oneshield.dms.service.DocumentService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/dms/api")
public class DocumentController {

    @Autowired
    DocumentService documentService;

    @PostMapping("/contexts")
    public ResponseEntity<?> createContext(@Valid @RequestBody AddContextDTO contextWrapper) {
	AddContextResponseDTO contextDto = documentService.saveContext(contextWrapper);
	contextDto.setStatus(DMSResponseActionStatus.CREATED);
	return ResponseEntity.ok(contextDto);
    }

    @GetMapping("/getDocuments")
    public ResponseEntity<Map<String, List<DMSDocument>>> getAllDocuments() {
	Map<String, List<DMSDocument>> a = new HashMap<>();
	List<DMSDocument> doclist = documentService.getAllDocuments();
	a.put("Documents", doclist);
	return new ResponseEntity<>(a, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/documents")
    public ResponseEntity<?> createDocument(@Valid @RequestBody List<DMSDocument> documentsToSave) {
	for (DMSDocument document : documentsToSave) {
	    documentService.saveDocument(document);
	}
	return ResponseEntity.ok().build();
    }

    @GetMapping("/contexts/getDocumentsList")
    public ResponseEntity<?> getContextByAssociateObjectId(@RequestParam("contextObject") Long id) {
	return ResponseEntity.ok().body(documentService.getContextDmsIdFromAssociateObjectId(id));
    }

    @PutMapping("/contexts/{assocId}/{dmsId}")
    public ResponseEntity<?> updateDocumentFromAssocIdAndDmsId(@PathVariable Long assocId,@PathVariable String dmsId, @Valid @RequestBody DocumentContentDTO documentToUpdate) {
	documentService.updateDocumentWithAssocIdAndDmsId(assocId, dmsId, documentToUpdate);
	return ResponseEntity.ok().build();
    }

    @DeleteMapping("/documents/{id}")
    public ResponseEntity<?> deleteDocument(@PathVariable Long id) {
	documentService.deleteDocumentById(id);
	return ResponseEntity.ok().body("Document Deleted");
    }

    @PutMapping("/documents/{id}")
    public ResponseEntity<?> updateDocument(@PathVariable Long id, @Valid @RequestBody DMSDocument documentToUpdate) {
	documentService.updateDocumentForId(id, documentToUpdate);
	return ResponseEntity.ok().body(documentService.getDocumentbyID(id));
    }
}
