package com.oneshield.dms.common;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DMSResponseActionStatus {

    CREATED("Created"), DELETED("Deleted"), EMPTY("Not Found"), NOACTION("NoAction"), FAILED("Failed"), ADDED("Added");

    private String displayName;

    @JsonValue
    public String getDisplayName() {
	return displayName;
    }

    private DMSResponseActionStatus(String displayName) {
	this.displayName = displayName;
    }

}
