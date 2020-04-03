package com.oneshield.dms.service;

import com.oneshield.dms.DTO.AddContextDTO;
import com.oneshield.dms.DTO.AddContextResponseDTO;
import com.oneshield.dms.DTO.DocumentContentDTO;
import com.oneshield.dms.common.DMSDocumentStatus;

public interface DocumentService {

    AddContextResponseDTO saveCreateContextAndDocuments(AddContextDTO contextDTO);

    AddContextResponseDTO getContextDmsIdFromContextObjectId(Long contextObjectId);

    AddContextResponseDTO getContextDmsIdFromParentContextObjectId(Long parentContextObjectId);

    AddContextResponseDTO getContextDmsIdFromMasterContextObjectId(Long masterContextObjectId);

    AddContextResponseDTO updateDocumentWithAssocIdAndDmsId(Long assocId, String dmsId,
	    DocumentContentDTO documentToUpdate);

    AddContextResponseDTO getDocumentByDmsId(String dmsId);

    boolean updateDocumentForDmsId(String dmsId, DMSDocumentStatus documentToUpdate);

    boolean deleteDocumentByDmsId(String dmsId);

    AddContextResponseDTO updateDocumentsForContextObjectId(AddContextDTO contextObjectWithListOfDocuments);

    boolean isExternalDmsIdUnique(String externalDmsId);
}
