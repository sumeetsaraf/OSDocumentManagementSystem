package com.oneshield.dms.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.oneshield.dms.DTO.AddContextDTO;
import com.oneshield.dms.DTO.AddContextResponseDTO;
import com.oneshield.dms.DTO.AddDocumentResponseDTO;
import com.oneshield.dms.DTO.DocumentContentDTO;
import com.oneshield.dms.common.DMSResponseActionStatus;
import com.oneshield.dms.domain.DMSContext;
import com.oneshield.dms.domain.DMSDocumentContent;
import com.oneshield.dms.domain.DMSDocumentHistory;
import com.oneshield.dms.domain.DMSDocument;
import com.oneshield.dms.domain.repository.DocumentHistoryRepository;
import com.oneshield.dms.domain.repository.DocumentContentRepository;
import com.oneshield.dms.domain.repository.ContextRepository;
import com.oneshield.dms.domain.repository.DocumentRepository;
import com.oneshield.dms.service.DocumentService;

@Service
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
public class DocumentServiceImpl implements DocumentService {

    public DocumentServiceImpl() {
	super();
	modelMapper = new ModelMapper();
    }

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    DocumentContentRepository documentContentRepository;

    @Autowired
    DocumentHistoryRepository documentContentHistoryRepository;

    @Autowired
    ContextRepository documentContextRepository;

    ModelMapper modelMapper;

    @Override
    public String getHello() {
	return "Hello from Document Service";
    }

    @Override
    public DMSDocument saveDocument(DMSDocument documentToSave) {
	// documentToSave.getDocumentcontent().setDocument(documentToSave);
	return documentRepository.save(documentToSave);
    }

    @Override
    public List<DMSDocument> getAllDocuments() {
	List<DMSDocument> documentList = documentRepository.findAll();

	if (documentList.size() > 0) {
	    return documentList;
	} else {
	    return new ArrayList<DMSDocument>();
	}

    }

    @Override
    public DMSDocument getDocumentbyID(Long Id) {
	DMSDocument document = documentRepository.findOne(Id);
	return document;

    }

    public void deleteDocumentById(Long id) {
	documentRepository.delete(id);
    }

    @Override
    public void updateDocumentForId(Long id, DMSDocument documentToUpdate) {
	DMSDocument oldDocument = documentRepository.findOne(id);
	// oldDocument.setAssociateObjectId(documentToUpdate.getAssociateObjectId());
	// oldDocument.setAssociateObjectType(documentToUpdate.getAssociateObjectType());
	// DMSDocument oldDocumentClone = oldDocument.clone();

	if (documentToUpdate.getDocumentContent() != null
		&& documentToUpdate.getDocumentContent().getDocumentContent() != null) {
	    oldDocument.getDocumentContent()
		    .setDocumentContent(documentToUpdate.getDocumentContent().getDocumentContent());
	}
	documentRepository.save(oldDocument);
    }

    @Override
    public AddContextResponseDTO saveContext(AddContextDTO inputDTO) {
	AddContextResponseDTO responseDto = new AddContextResponseDTO();
	DMSContext contextDomain = documentContextRepository.findByAssociateObjectId(inputDTO.getAssociateObjectId());
	if (contextDomain == null) {
	    contextDomain = modelMapper.map(inputDTO, DMSContext.class);
	}
	for (DocumentContentDTO documentDTO : inputDTO.getDocumentContent()) {
	    contextDomain = this.createNewDocumentFromDtoForContext(documentDTO, contextDomain);
	    contextDomain = documentContextRepository.saveAndFlush(contextDomain);
	    AddDocumentResponseDTO responseIntermDto = new AddDocumentResponseDTO();
	    responseIntermDto.setDmsId(contextDomain.getSetOfDocuments().stream().sorted(new Comparator<DMSDocument>() {
		@Override
		public int compare(DMSDocument o1, DMSDocument o2) {
		    return o2.getId().compareTo(o1.getId());
		}
	    }).findFirst().get().getDmsId());
	    responseIntermDto.setResponseStatus(DMSResponseActionStatus.CREATED);
	    responseDto.getResponseResult().add(responseIntermDto);
	}
	return responseDto;

    }

    private DMSContext createNewDocumentFromDtoForContext(DocumentContentDTO documentDTO, DMSContext existingContext) {
	DMSDocumentContent contentDomain = modelMapper.map(documentDTO, DMSDocumentContent.class);
	DMSDocument documentDomain = existingContext.createNewDocument();
	documentDomain.setFileName(documentDTO.getFileName());
	documentDomain.setDocumentContent(contentDomain);
	return existingContext;
    }

    @Override
    public AddContextResponseDTO getContextDmsIdFromAssociateObjectId(Long id) {
	DMSContext contextDomain = documentContextRepository.findByAssociateObjectId(id);
	AddContextResponseDTO dtoToReturn = new AddContextResponseDTO();
	if (contextDomain == null) {
	    dtoToReturn.setStatus(DMSResponseActionStatus.EMPTY);
	} else {
	    for (DMSDocument document : contextDomain.getSetOfDocuments()) {
		AddDocumentResponseDTO responseIntermDto = new AddDocumentResponseDTO();
		responseIntermDto = modelMapper.map(document, AddDocumentResponseDTO.class);
		dtoToReturn.getResponseResult().add(responseIntermDto);
	    }
	}

	return dtoToReturn;
    }

    @Override
    public AddContextResponseDTO updateDocumentWithAssocIdAndDmsId(Long assocId, String dmsId,
	    DocumentContentDTO documentToUpdate) {
	DMSContext contextDomain = documentContextRepository.findByAssociateObjectId(assocId);
	DMSDocument documentFromDb = null;
	if (contextDomain != null) {
	    documentFromDb = contextDomain.getSetOfDocuments().parallelStream()
		    .filter(p -> p.getDmsId().equalsIgnoreCase(dmsId)).findFirst().get();
	}
	if (documentFromDb!=null){
	    DMSDocumentHistory history = new DMSDocumentHistory();
	    history.setDocument(documentFromDb);
	    if(documentToUpdate.getDocumentContent() != null) {
		DMSDocumentContent contentDomain = modelMapper.map(documentToUpdate, DMSDocumentContent.class);
		history.setDocumentContent(documentFromDb.getDocumentContent());
		documentFromDb.getHistoryOfDocument().add(history);
		documentFromDb.setDocumentContent(contentDomain);
	    }
	    if(documentToUpdate.getFileName() != null && !documentToUpdate.getFileName().isEmpty()) {
		history.setFileName(documentFromDb.getFileName());
		history.setDocumentContent(documentFromDb.getDocumentContent());
		documentFromDb.getHistoryOfDocument().add(history);
		documentFromDb.setFileName(documentToUpdate.getFileName());
		
	    }
	    documentContextRepository.saveAndFlush(contextDomain);
	}
	return null;
    }

}
