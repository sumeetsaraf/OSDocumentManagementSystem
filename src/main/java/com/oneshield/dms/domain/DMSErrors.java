package com.oneshield.dms.domain;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;

import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "DMS_ERRORS")
public class DMSErrors {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreatedDate
    @Temporal(TIMESTAMP)
    private Date createdDate;

    private Integer statusValue;

    @Lob
    private String message;

    @Lob
    private byte[] requestJson;

    private String errors;

    private String clientIpAddress;
    @Lob
    private String exceptionMessage;

    public DMSErrors() {
	super();
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Date getCreatedDate() {
	return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
	this.createdDate = createdDate;
    }

    public String getMessage() {
	return message;
    }

    public void setMessage(String message) {
	this.message = message;
    }

    public String getErrors() {
	return errors;
    }

    public void setErrors(String errors) {
	this.errors = errors;
    }

    public String getExceptionMessage() {
	return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
	this.exceptionMessage = exceptionMessage;
    }

    @PrePersist
    private void beforeAnyOperation() {
	this.setCreatedDate(new Date());
    }

    public byte[] getRequestJson() {
	return requestJson;
    }

    public void setRequestJson(byte[] requestJson) {
	this.requestJson = requestJson;
    }

    public Integer getStatusValue() {
	return statusValue;
    }

    public void setStatusValue(Integer statusValue) {
	this.statusValue = statusValue;
    }

    public String getClientIpAddress() {
	return clientIpAddress;
    }

    public void setClientIpAddress(String clientIPAddress) {
	this.clientIpAddress = clientIPAddress;
    }

}
