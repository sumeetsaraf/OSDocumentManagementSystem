package com.oneshield.dms.service.impl;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

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
import com.oneshield.dms.common.DMSDocumentStatus;
import com.oneshield.dms.common.DMSResponseActionStatus;
import com.oneshield.dms.common.DMSSystemParameters;
import com.oneshield.dms.domain.DMSContext;
import com.oneshield.dms.domain.DMSDocument;
import com.oneshield.dms.domain.DMSDocumentContent;
import com.oneshield.dms.domain.DMSDocumentHistory;
import com.oneshield.dms.domain.repository.ContextRepository;
import com.oneshield.dms.domain.repository.DocumentRepository;
import com.oneshield.dms.service.DocumentService;

@Service
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
public class DocumentServiceImpl /* extends BaseService */ implements DocumentService {

    @Autowired
    ContextRepository documentContextRepository;

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    DMSSystemParameters systemParameters;

    ModelMapper modelMapper;

    public DocumentServiceImpl() {
	super();
	modelMapper = new ModelMapper();
    }

    @Override
    public AddContextResponseDTO saveCreateContextAndDocuments(AddContextDTO inputDTO) {
	AddContextResponseDTO responseDto = new AddContextResponseDTO();
	DMSContext contextDomain = documentContextRepository.findByContextObjectId(inputDTO.getContextObjectId());
	if (contextDomain == null) {
	    contextDomain = modelMapper.map(inputDTO, DMSContext.class);
	} else {
	    if (inputDTO.getParentContextObjectId() != null) {
		contextDomain.setParentContextObjectId(inputDTO.getParentContextObjectId());
	    }
	}
	for (DocumentContentDTO documentDTO : inputDTO.getDocumentContent()) {
	    contextDomain = this.createNewDocumentFromDtoForContext(documentDTO, contextDomain);
	    this.updateDocumentStatusBasedOnOptimistic(contextDomain, systemParameters.isOptimisticMode());
	    contextDomain = documentContextRepository.saveAndFlush(contextDomain);
	    DMSDocument createdDocument = contextDomain.getSetOfDocuments().stream()
		    .sorted(new Comparator<DMSDocument>() {
			@Override
			public int compare(DMSDocument o1, DMSDocument o2) {
			    return o2.getId().compareTo(o1.getId());
			}
		    }).findFirst().get();
	    AddDocumentResponseDTO responseIntermDto = modelMapper.map(createdDocument, AddDocumentResponseDTO.class);
	    responseIntermDto.setDocumentContent(null);
	    responseDto.getResponseResult().add(responseIntermDto);
	}
	return responseDto;
    }

    private void updateDocumentStatusBasedOnOptimistic(DMSContext contextDomain, boolean optimisticMode) {
	for (DMSDocument document : contextDomain.getSetOfDocuments()) {
	    if (document.getId() == null) {// only update non persisted records.
		document.setDocumentStatus(optimisticMode ? DMSDocumentStatus.ADDED : DMSDocumentStatus.CREATED);
	    }
	}

    }

    private DMSContext createNewDocumentFromDtoForContext(DocumentContentDTO documentDTO, DMSContext existingContext) {
	DMSDocumentContent contentDomain = modelMapper.map(documentDTO, DMSDocumentContent.class);
	DMSDocument documentDomain = existingContext.createNewDocument();
	modelMapper.map(documentDTO, documentDomain);
	if (contentDomain.getDocumentContent() != null) {
	    documentDomain.setDocumentContent(contentDomain);
	}
	return existingContext;
    }

    @Override
    public AddContextResponseDTO getContextDmsIdFromContextObjectIdOrParentContextObjectId(Long associateId,
	    Long associateParent) {
	return this.createDTOFromContextDomainSet(
		documentContextRepository.findByContextObjectIdOrParentContextObjectId(associateId, associateParent));
    }

    private AddContextResponseDTO createDTOFromContextDomainSet(Set<DMSContext> contextDomainSet) {
	AddContextResponseDTO dtoToReturn = new AddContextResponseDTO();
	dtoToReturn.setResponseStatus(DMSResponseActionStatus.EMPTY);
	contextDomainSet.remove(null);
	for (DMSContext context : contextDomainSet) {
	    context.getSetOfDocuments().remove(null);
	    for (DMSDocument document : context.getSetOfDocuments()) {
		if (document.getDocumentStatus().equals(DMSDocumentStatus.DELETED)) {
		    continue;
		}
		AddDocumentResponseDTO responseIntermDto = new AddDocumentResponseDTO();
		responseIntermDto = modelMapper.map(document, AddDocumentResponseDTO.class);
		responseIntermDto.setDocumentContent(null);
		dtoToReturn.getResponseResult().add(responseIntermDto);
	    }
	    dtoToReturn.setResponseStatus(DMSResponseActionStatus.NOACTION);
	}
	return dtoToReturn;
    }

    @Override
    public AddContextResponseDTO updateDocumentWithAssocIdAndDmsId(Long assocId, String dmsId,
	    DocumentContentDTO documentToUpdate) {
	DMSContext contextDomain = documentContextRepository.findByContextObjectId(assocId);
	DMSDocument documentFromDb = null;
	if (contextDomain != null) {
	    documentFromDb = contextDomain.getSetOfDocuments().parallelStream().filter(p -> p.getDmsId().equals(dmsId))
		    .findFirst().get();
	}
	if (documentFromDb != null) {
	    DMSDocumentHistory history = new DMSDocumentHistory();
	    history.setDocument(documentFromDb);
	    if (documentToUpdate.getDocumentContent() != null) {
		DMSDocumentContent contentDomain = modelMapper.map(documentToUpdate, DMSDocumentContent.class);
		history.setDocumentContent(documentFromDb.getDocumentContent());
		documentFromDb.getHistoryOfDocument().add(history);
		documentFromDb.setDocumentContent(contentDomain);
	    }
	    if (documentToUpdate.getFileName() != null && !documentToUpdate.getFileName().isEmpty()) {
		history.setFileName(documentFromDb.getFileName());
		history.setDocumentContent(documentFromDb.getDocumentContent());
		documentFromDb.getHistoryOfDocument().add(history);
		documentFromDb.setFileName(documentToUpdate.getFileName());

	    }
	    documentContextRepository.saveAndFlush(contextDomain);
	}
	return null;
    }

    @Override
    public AddContextResponseDTO getDocumentByDmsId(String dmsId) {
	AddContextResponseDTO responseDto = new AddContextResponseDTO();
	DMSDocument document = documentRepository.findByDmsIdAndDocumentStatusNot(dmsId, DMSDocumentStatus.DELETED);
	AddDocumentResponseDTO responseIntermDto = new AddDocumentResponseDTO();
	if (document == null) {
	    responseDto.setResponseStatus(DMSResponseActionStatus.EMPTY);
	} else {
	    responseIntermDto = modelMapper.map(document, AddDocumentResponseDTO.class);
	    responseDto.setResponseStatus(DMSResponseActionStatus.NOACTION);
	    responseDto.getResponseResult().add(responseIntermDto);
	}
	return responseDto;
    }

    @Override
    public boolean updateDocumentForDmsId(String dmsId, DMSDocumentStatus statusToUpdate) {
	DMSDocument document = documentRepository.findByDmsIdAndDocumentStatusNot(dmsId, DMSDocumentStatus.DELETED);
	if (document != null) {
	    document.setDocumentStatus(statusToUpdate);
	    documentRepository.save(document);
	    return true;
	} else {
	    return false;
	}
    }

    @Override
    public boolean deleteDocumentByDmsId(String dmsId) {
	return this.updateDocumentForDmsId(dmsId, DMSDocumentStatus.DELETED);
    }

    @Override
    public AddContextResponseDTO getContextDmsIdFromContextObjectId(Long contextObjectId) {
	return this.createDTOFromContextDomainSet(
		new HashSet<>(Arrays.asList(documentContextRepository.findByContextObjectId(contextObjectId))));
    }

    @Override
    public AddContextResponseDTO getContextDmsIdFromParentContextObjectId(Long parentContextObjectId) {
	return this.createDTOFromContextDomainSet(
		documentContextRepository.findByParentContextObjectId(parentContextObjectId));
    }

    @Override
    public AddContextResponseDTO getContextDmsIdFromMasterContextObjectId(Long masterContextObjectId) {
	return this.createDTOFromContextDomainSet(
		documentContextRepository.findByMasterContextObjectId(masterContextObjectId));
    }

}
