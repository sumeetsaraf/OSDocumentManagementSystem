package com.oneshield.dms.service;

import org.springframework.web.context.request.WebRequest;

import com.oneshield.dms.DTO.ApiError;

public interface ErrorService {

    boolean storeExceptionWithRequest(ApiError errorDto, Exception exception, WebRequest request);
}
