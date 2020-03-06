package com.oneshield.dms.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.oneshield.dms.DTO.ApiError;
import com.oneshield.dms.domain.DMSErrors;
import com.oneshield.dms.domain.repository.ErrorRepository;
import com.oneshield.dms.service.ErrorService;

@Service
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
public class PersistErrorIntoDatabaseServiceImpl implements ErrorService {

    @Autowired
    ErrorRepository errorRepo;

    ModelMapper modelMapper;

    public PersistErrorIntoDatabaseServiceImpl() {
	super();
	modelMapper = new ModelMapper();
    }

    @Override
    public boolean storeExceptionWithRequest(ApiError errorDto, Exception exception, WebRequest request){
	DMSErrors errorToPersist = modelMapper.map(errorDto, DMSErrors.class);
	errorToPersist.setStatusValue(errorDto.getStatus().value());
	errorToPersist.setExceptionMessage(exception.getMessage());
	errorToPersist.setRequestJson(((ContentCachingRequestWrapper)((ServletWebRequest) request).getRequest()).getContentAsByteArray());
	errorToPersist.setClientIpAddress(((ServletWebRequest) request).getRequest().getRemoteAddr());
	errorRepo.save(errorToPersist);
	return true;
    }

}
