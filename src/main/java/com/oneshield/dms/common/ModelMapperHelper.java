package com.oneshield.dms.common;

import org.modelmapper.ModelMapper;

import com.oneshield.dms.DTO.AddDocumentResponseDTO;
import com.oneshield.dms.domain.DMSDocument;

public class ModelMapperHelper {

    public static void setupDocumentSpecificModelMapper(ModelMapper modelMapper) {
	modelMapper.typeMap(DMSDocument.class, AddDocumentResponseDTO.class)
	    .addMappings(mapper -> mapper.skip(AddDocumentResponseDTO::setDocumentContent));
    }
}
