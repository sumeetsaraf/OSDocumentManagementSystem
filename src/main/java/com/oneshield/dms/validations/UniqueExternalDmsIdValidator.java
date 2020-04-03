package com.oneshield.dms.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oneshield.dms.DTO.AddContextDTO;
import com.oneshield.dms.DTO.DocumentContentDTO;
import com.oneshield.dms.service.DocumentService;

@Component
public class UniqueExternalDmsIdValidator
	implements ConstraintValidator<UniqueExternalDmsId, AddContextDTO> {

    @Autowired
    DocumentService documentService;

    @Override
    public boolean isValid(AddContextDTO value, ConstraintValidatorContext context) {
	boolean isValid = true;
	if (value == null) {
	    isValid = true;
	} else {
	    for (DocumentContentDTO document : value.getListOfDocuments()) {
		isValid = (document.getExternalDmsId() == null || document.getExternalDmsId().isEmpty())
			|| (document.getExternalDmsId() != null && !document.getExternalDmsId().isEmpty()
				&& documentService.isExternalDmsIdUnique(document.getExternalDmsId()));

		if (!isValid) {
		    break;
		}
	    }
	}
	return isValid;
    }

    @Override
    public void initialize(
                           UniqueExternalDmsId constraintAnnotation)
    {
        // TODO Auto-generated method stub
        
    }
}
