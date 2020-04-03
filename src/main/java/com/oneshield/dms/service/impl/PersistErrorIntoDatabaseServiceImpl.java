package com.oneshield.dms.service.impl;

import java.util.List;
import java.util.concurrent.Future;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.oneshield.dms.DTO.ApiError;
import com.oneshield.dms.common.DMSSystemParameters;
import com.oneshield.dms.domain.DMSErrors;
import com.oneshield.dms.domain.repository.ErrorRepository;
import com.oneshield.dms.service.ErrorService;

@Service
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
@EnableAsync
public class PersistErrorIntoDatabaseServiceImpl implements ErrorService {

    @Autowired
    ErrorRepository errorRepo;
    @Autowired
    DMSSystemParameters systemParameters;

    ModelMapper modelMapper;

    public PersistErrorIntoDatabaseServiceImpl() {
	super();
	modelMapper = new ModelMapper();
    }

    @Async
    @Override
    public Future<Boolean> storeExceptionWithRequest(ApiError errorDto, Exception exception, WebRequest request) {
	if (!systemParameters.isPersistError()) {
	    return new AsyncResult<Boolean>(false);
	}
	DMSErrors errorToPersist = modelMapper.map(errorDto, DMSErrors.class);
	errorToPersist.setStatusValue(errorDto.getStatus().value());
	errorToPersist.setExceptionMessage(exception.getMessage());
	errorToPersist.setRequestJson(
		((ContentCachingRequestWrapper) ((ServletWebRequest) request).getRequest()).getContentAsByteArray());
	// Getting header X-FORWARDED-FOR to cater load balancer needs in future.
	String clientIpAddress = request.getHeader("X-FORWARDED-FOR") == null
		|| request.getHeader("X-FORWARDED-FOR").isEmpty()
			? ((ServletWebRequest) request).getRequest().getRemoteAddr()
			: request.getHeader("X-FORWARDED-FOR");
	errorToPersist.setClientIpAddress(clientIpAddress);
	errorRepo.save(errorToPersist);
	return new AsyncResult<Boolean>(true);
    }

    @Override
    public List<DMSErrors> getAllErrors() {
	return errorRepo.findAll();
    }

}
