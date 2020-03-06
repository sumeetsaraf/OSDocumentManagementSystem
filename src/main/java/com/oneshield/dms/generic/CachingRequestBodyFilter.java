package com.oneshield.dms.generic;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Component
public class CachingRequestBodyFilter extends OncePerRequestFilter {

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	    throws ServletException, IOException {
	HttpServletRequest currentRequest = (HttpServletRequest) request;
	ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(currentRequest);
	filterChain.doFilter(wrappedRequest, response);

    }
}
