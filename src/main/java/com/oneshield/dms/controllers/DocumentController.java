package com.oneshield.dms.controllers;

import javax.validation.Valid;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oneshield.dms.DTO.AddContextDTO;
import com.oneshield.dms.DTO.AddContextResponseDTO;
import com.oneshield.dms.DTO.AddDocumentResponseDTO;
import com.oneshield.dms.common.DMSDocumentStatus;
import com.oneshield.dms.common.DMSResponseActionStatus;
import com.oneshield.dms.service.DocumentService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class DocumentController {

    @Autowired
    DocumentService documentService;

    Tika tika;

    public DocumentController() {
	tika = new Tika();
    }

    @PostMapping("/documents")
    public ResponseEntity<?> createContextFromDocuments(@Valid @RequestBody AddContextDTO contextWrapper) {
	AddContextResponseDTO contextDto = documentService.saveCreateContextAndDocuments(contextWrapper);
	contextDto.setResponseStatus(DMSResponseActionStatus.CREATED);
	return ResponseEntity.status(HttpStatus.CREATED).body(contextDto);
    }

    @GetMapping("/documents")
    public ResponseEntity<?> getDocumentOrContext(
	    @RequestParam(value = "contextObjectId", required = false) String contextObjectId,
	    @RequestParam(value = "parentContextObjectId", required = false) String parentContextObjectId,
	    @RequestParam(value = "masterContextObjectId", required = false) String masterContextObjectIdObjectId,
	    @RequestParam(value = "dmsId", required = false) String dmsId) {
	AddContextResponseDTO dto = null;
	if (contextObjectId != null && !contextObjectId.isEmpty()) {
	    dto = documentService.getContextDmsIdFromContextObjectId(Long.parseLong(contextObjectId));
	} else if (parentContextObjectId != null && !parentContextObjectId.isEmpty()) {
	    dto = documentService.getContextDmsIdFromParentContextObjectId(Long.parseLong(parentContextObjectId));
	} else if (masterContextObjectIdObjectId != null && !masterContextObjectIdObjectId.isEmpty()) {
	    dto = documentService
		    .getContextDmsIdFromMasterContextObjectId(Long.parseLong(masterContextObjectIdObjectId));
	} else if (dmsId != null && !dmsId.isEmpty()) {
	    dto = documentService.getDocumentByDmsId(dmsId);
	} else {
	    dto = new AddContextResponseDTO();
	    dto.setResponseStatus(DMSResponseActionStatus.EMPTY);
	}
	if (dto.getResponseStatus().equals(DMSResponseActionStatus.EMPTY)) {
	    dto.getResponseResult().add(AddDocumentResponseDTO.returnEmpty());
	}
	return ResponseEntity.ok(dto);
    }

    @GetMapping("/documentsContent")
    public ResponseEntity<?> getDocumentContent(@RequestParam(value = "dmsId", required = true) String dmsId) {
	if (dmsId != null && !dmsId.isEmpty()) {
	    AddContextResponseDTO responseDto = documentService.getDocumentByDmsId(dmsId);
	    if (responseDto.getResponseStatus().equals(DMSResponseActionStatus.EMPTY)) {
		return ResponseEntity.notFound().build();
	    }
	    AddDocumentResponseDTO documentResponse = responseDto.getResponseResult().stream().findFirst().get();
	    String tikaDetection = tika.detect(documentResponse.getDocumentContent());
	    return ResponseEntity.status(HttpStatus.OK).header("Content-Disposition", "inline")
		    .header("Content-Type", tikaDetection).body(documentResponse.getDocumentContent());
	} else {
	    return ResponseEntity.notFound().build();
	}
    }

    @DeleteMapping("/documents")
    public ResponseEntity<?> deleteDocument(@RequestParam("dmsId") String dmsId) {
	boolean existAndDeleted = documentService.deleteDocumentByDmsId(dmsId);
	AddContextResponseDTO dto = new AddContextResponseDTO();
	if (!existAndDeleted) {
	    dto.setResponseStatus(DMSResponseActionStatus.EMPTY);
	    dto.getResponseResult().add(AddDocumentResponseDTO.returnEmpty());
	} else {
	    dto.setResponseStatus(DMSResponseActionStatus.DELETED);
	    AddDocumentResponseDTO innerDto = new AddDocumentResponseDTO();
	    innerDto.setDmsId(dmsId);
	    innerDto.setDocumentStatus(DMSResponseActionStatus.DELETED);
	    dto.getResponseResult().add(innerDto);
	}
	return ResponseEntity.ok().body(dto);// existAndDeleted ? ResponseEntity.noContent().build() :
					     // ResponseEntity.notFound().build();
    }

    @PutMapping("/documents")
    public ResponseEntity<?> updateDocumentWithStatus(@RequestParam("dmsId") String dmsId,
	    @Valid @RequestBody DMSDocumentStatus statusToUpdate) {
	boolean existAndUpdated = documentService.updateDocumentForDmsId(dmsId, statusToUpdate);
	return existAndUpdated ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/health")
    public ResponseEntity<?> dummy() {
	DMSDocumentStatus dto = DMSDocumentStatus.FAILED;
	return ResponseEntity.ok().body(dto);
    }
}
