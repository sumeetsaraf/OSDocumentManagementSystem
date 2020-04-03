package com.oneshield.dms.service;

import java.util.List;
import java.util.concurrent.Future;

import org.springframework.web.context.request.WebRequest;

import com.oneshield.dms.DTO.ApiError;
import com.oneshield.dms.domain.DMSErrors;

public interface ErrorService {

    Future<Boolean> storeExceptionWithRequest(ApiError errorDto, Exception exception, WebRequest request);
    
    List<DMSErrors> getAllErrors();
}
