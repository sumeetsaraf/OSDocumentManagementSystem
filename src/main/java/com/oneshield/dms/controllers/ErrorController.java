package com.oneshield.dms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oneshield.dms.service.ErrorService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/errorsLog") // this should never be made error, this way we may kill future support for
				  // spring default /error api
public class ErrorController {

    @Autowired
    ErrorService errorService;

    @GetMapping
    public ResponseEntity<?> getCompleteErrorLog() {
	return ResponseEntity.ok().body(errorService.getAllErrors());
    }
}
