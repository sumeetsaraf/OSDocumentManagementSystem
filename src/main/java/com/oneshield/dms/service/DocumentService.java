package com.oneshield.dms.service;

import java.util.List;

import com.oneshield.dms.DTO.AddContextDTO;
import com.oneshield.dms.DTO.AddContextResponseDTO;
import com.oneshield.dms.DTO.DocumentContentDTO;
import com.oneshield.dms.domain.DMSDocument;

public interface DocumentService {

    String getHello();

    public DMSDocument saveDocument(DMSDocument documentToSave);

    public List<DMSDocument> getAllDocuments();

    public DMSDocument getDocumentbyID(Long Id);

    public void deleteDocumentById(Long id);

    void updateDocumentForId(Long id, DMSDocument documentToUpdate);

    AddContextResponseDTO saveContext(AddContextDTO contextDTO);

    AddContextResponseDTO getContextDmsIdFromAssociateObjectId(Long id);

    AddContextResponseDTO updateDocumentWithAssocIdAndDmsId(Long assocId, String dmsId,
	    DocumentContentDTO documentToUpdate);
}
