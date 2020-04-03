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
import com.oneshield.dms.common.ModelMapperHelper;
import com.oneshield.dms.domain.DMSContext;
import com.oneshield.dms.domain.DMSDocument;
import com.oneshield.dms.domain.DMSDocumentContent;
import com.oneshield.dms.domain.DMSDocumentHistory;
import com.oneshield.dms.domain.repository.ContextRepository;
import com.oneshield.dms.domain.repository.DocumentContentRepository;
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

    @Autowired
    DocumentContentRepository documentContentRepository;
    
    ModelMapper modelMapper;

    public DocumentServiceImpl() {
	super();
	modelMapper = new ModelMapper();
	ModelMapperHelper.setupDocumentSpecificModelMapper(modelMapper);

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
	    if (inputDTO.getMasterContextObjectId() != null) {
		contextDomain.setMasterContextObjectId(inputDTO.getMasterContextObjectId());
	    }
	}
	this.createDocumentsInContextFromDocumentDTOList(inputDTO, responseDto, contextDomain);
	return responseDto;
    }

    private void createDocumentsInContextFromDocumentDTOList(AddContextDTO inputDTO, AddContextResponseDTO responseDto,
	    DMSContext contextDomain) {
	for (DocumentContentDTO documentDTO : inputDTO.getListOfDocuments()) {
	    contextDomain = this.createDocumentFromDTO(contextDomain, documentDTO);
	    contextDomain = documentContextRepository.saveAndFlush(contextDomain);
	    DMSDocument createdDocument = contextDomain.getSetOfDocuments().stream()
		    .sorted(new Comparator<DMSDocument>() {
			@Override
			public int compare(DMSDocument o1, DMSDocument o2) {
			    return o2.getId().compareTo(o1.getId());
			}
		    }).findFirst().get();
	    AddDocumentResponseDTO responseIntermDto = modelMapper.map(createdDocument, AddDocumentResponseDTO.class);
	    responseDto.getResponseResult().add(responseIntermDto);
	}
    }

    private DMSContext createDocumentFromDTO(DMSContext contextDomain, DocumentContentDTO documentDTO) {
	contextDomain = this.createNewDocumentFromDtoForContext(documentDTO, contextDomain);
	this.updateDocumentStatusBasedOnOptimistic(contextDomain, systemParameters.isOptimisticMode());
	return contextDomain;
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
		dtoToReturn.getResponseResult().add(responseIntermDto);
		dtoToReturn.setResponseStatus(DMSResponseActionStatus.NOACTION);
	    }
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
	DMSDocument document = documentRepository.findByDmsIdOrExternalDmsIdAndDocumentStatusNot(dmsId, dmsId,
		DMSDocumentStatus.DELETED);
	AddDocumentResponseDTO responseIntermDto = new AddDocumentResponseDTO();
	if (document == null) {
	    responseDto.setResponseStatus(DMSResponseActionStatus.EMPTY);
	} else {
	    modelMapper.typeMap(DMSDocument.class, AddDocumentResponseDTO.class).addMapping(
		    src -> src.getDocumentContent().getDocumentContent(),
		    (dest, value) -> dest.setDocumentContent((byte[]) value));
	    responseIntermDto = modelMapper.map(document, AddDocumentResponseDTO.class);
	    modelMapper.typeMap(DMSDocument.class, AddDocumentResponseDTO.class)
	    .addMappings(mapper -> mapper.skip(AddDocumentResponseDTO::setDocumentContent));
	    responseDto.setResponseStatus(DMSResponseActionStatus.NOACTION);
	    responseDto.getResponseResult().add(responseIntermDto);
	}
	return responseDto;
    }

    @Override
    public boolean updateDocumentForDmsId(String dmsId, DMSDocumentStatus statusToUpdate) {
	DMSDocument document = documentRepository.findByDmsIdOrExternalDmsIdAndDocumentStatusNot(dmsId, dmsId,
		DMSDocumentStatus.DELETED);
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

    @Override
    public AddContextResponseDTO updateDocumentsForContextObjectId(AddContextDTO contextObjectWithListOfDocuments) {
	DMSContext contextDomain = documentContextRepository
		.findByContextObjectId(contextObjectWithListOfDocuments.getContextObjectId());
	AddContextResponseDTO contextResponseDto = null;
	if (contextDomain == null) {
	    contextResponseDto = this.saveCreateContextAndDocuments(contextObjectWithListOfDocuments);
	} else {
	    contextResponseDto = this.updateContextObjectAttributesManually(contextDomain,
		    contextObjectWithListOfDocuments);
	}
	contextResponseDto.setResponseStatus(DMSResponseActionStatus.SYNCHED);
	return contextResponseDto;
    }

    private AddContextResponseDTO updateContextObjectAttributesManually(DMSContext contextObjectToUpdate,
	    AddContextDTO toUpdateFrom) {
	// manual cross assignment between DTO and Domain Object, as alot properties
	// needs to be ignored
	long dmsContextId = contextObjectToUpdate.getId();
	Set<DMSDocument> setOfDocuments = contextObjectToUpdate.getSetOfDocuments();
	DMSContext contextObjectToMerge = modelMapper.map(contextObjectToUpdate, DMSContext.class);
	modelMapper.map(toUpdateFrom, contextObjectToMerge);
	contextObjectToMerge.setId(dmsContextId);
	DMSDocument matchedDocument = null;
	for (DocumentContentDTO documentDTO : toUpdateFrom.getListOfDocuments()) {
	    matchedDocument = null;
	    // find the right document using externalDmsId, if externalDmsId == null then
	    // check against dmsId else null
	    matchedDocument = setOfDocuments.parallelStream()
		    .filter(document -> document.getExternalDmsId() != null
			    ? document.getExternalDmsId().equalsIgnoreCase(documentDTO.getExternalDmsId())
			    : document.getDmsId().equalsIgnoreCase(documentDTO.getExternalDmsId()))
		    .findFirst().orElse(null);
	    if (matchedDocument != null) {
		// if document found then update it manually
		mapDocumentPropertieFromDTOManually(matchedDocument, documentDTO);
		matchedDocument.setDocumentStatus(DMSDocumentStatus.SYNCHED);
		DMSDocumentContent newContent = documentDTO.getDocumentContent() != null ? new DMSDocumentContent()
			: null;
		if (newContent != null) {
		    documentContentRepository.delete(matchedDocument.getDocumentContent());
		    newContent.setDocumentContent(documentDTO.getDocumentContent());
		    matchedDocument.setDocumentContent(newContent);
		}
	    } else {
		// else create a new document and attach.
		contextObjectToMerge = this.createDocumentFromDTO(contextObjectToMerge, documentDTO);
	    }
	}
	documentContextRepository.saveAndFlush(contextObjectToMerge);
	return this.createDTOFromContextDomainSet(new HashSet<>(Arrays.asList(contextObjectToMerge)));
    }

    private void mapDocumentPropertieFromDTOManually(DMSDocument matchedDocument, DocumentContentDTO documentDTO) {
	matchedDocument.setContentCode(documentDTO.getContentFeatures().getContentCode() != null
		? documentDTO.getContentFeatures().getContentCode()
		: matchedDocument.getContentCode());
	matchedDocument.setContentType(documentDTO.getContentFeatures().getContentType() != null
		? documentDTO.getContentFeatures().getContentType()
		: matchedDocument.getContentType());
	matchedDocument.setVersionId(
		documentDTO.getVersionId() != null ? documentDTO.getVersionId() : matchedDocument.getVersionId());
	matchedDocument.setAttachmentType(documentDTO.getAttachmentType() != null ? documentDTO.getAttachmentType()
		: matchedDocument.getAttachmentType());
	matchedDocument.setRenderingType(documentDTO.getRenderingType() != null ? documentDTO.getRenderingType()
		: matchedDocument.getRenderingType());
	matchedDocument
		.setRenderingTemplate(documentDTO.getRenderingTemplate() != null ? documentDTO.getRenderingTemplate()
			: matchedDocument.getRenderingTemplate());
	matchedDocument.setRenderingTemplateId(
		documentDTO.getRenderingTemplateId() != null ? documentDTO.getRenderingTemplateId()
			: matchedDocument.getRenderingTemplateId());
	matchedDocument.setFileName(
		documentDTO.getFileName() != null ? documentDTO.getFileName() : matchedDocument.getFileName());
	matchedDocument.setContentCode(
		documentDTO.getContentFeatures() != null && documentDTO.getContentFeatures().getContentCode() != null
			? documentDTO.getContentFeatures().getContentCode()
			: matchedDocument.getContentCode());
	matchedDocument.setContentType(
		documentDTO.getContentFeatures() != null && documentDTO.getContentFeatures().getContentType() != null
			? documentDTO.getContentFeatures().getContentType()
			: matchedDocument.getContentType());
	matchedDocument
		.setDescription(documentDTO.getDocumentDescription() != null ? documentDTO.getDocumentDescription()
			: matchedDocument.getDescription());
	matchedDocument.setDocumentCode(documentDTO.getDocumentCode() != null ? documentDTO.getDocumentCode()
		: matchedDocument.getDocumentCode());
    }

    @Override
    @Transactional
    public boolean isExternalDmsIdUnique(String externalDmsId) {
	return documentRepository.findByDmsIdOrExternalDmsIdAndDocumentStatusNot(externalDmsId, externalDmsId,
		DMSDocumentStatus.DELETED) == null;
    }

}
