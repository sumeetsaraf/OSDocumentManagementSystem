package com.oneshield.dms.common;

public enum DMSDocumentStatus {

    CREATED("Created", 0), MODIFIED("Modified", 1), DELETED("Deleted", 2), FAILED("Failed", 3), ADDED("Added", 4), SYNCHED("Synched",5);

    // 0-Created + 4-Added:Pessimistic approach for document creation.
    // 1-Modified:Document has been modified
    // 2-Deleted:Document has been deleted
    // 3-Failed:Document creation on the 3rd party portal has failed.
    // 4-Added-Optimistic approach for document added.

    private String displayName;

    private int enumNumber;

    private DMSDocumentStatus(String displayName, int enumNumber) {
	this.setDisplayName(displayName);
	this.setEnumNumber(enumNumber);
    }

    public String getDisplayName() {
	return displayName;
    }

    public void setDisplayName(String displayName) {
	this.displayName = displayName;
    }

    public int getEnumNumber() {
	return enumNumber;
    }

    public void setEnumNumber(int enumNumber) {
	this.enumNumber = enumNumber;
    }

}
