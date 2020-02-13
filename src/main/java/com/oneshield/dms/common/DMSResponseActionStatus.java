package com.oneshield.dms.common;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DMSResponseActionStatus {

    ADDED("Added"), CREATED("Created"), DELETED("Deleted"), EMPTY("Empty");

    private String displayName;

    @JsonValue
    public String getDisplayName() {
	return displayName;
    }

    private DMSResponseActionStatus(String displayName) {
	this.displayName = displayName;
    }

}
