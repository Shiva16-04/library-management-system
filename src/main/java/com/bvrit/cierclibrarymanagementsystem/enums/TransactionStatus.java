package com.bvrit.cierclibrarymanagementsystem.enums;

public enum TransactionStatus {

    ISSUED("Issued"),
    RETURNED("Returned"),
    PENDING("Pending"),
    BOOK_ADDED("Book Added"),
    BOOK_REMOVED("Book Removed"),
    AUTHOR_ADDED("Author Added"),
    AUTHOR_REMOVED("Author Removed"),
    USER_ADDED("User Added"),
    USER_REMOVED("User Removed"),
    ADMIN_ADDED("Admin Added"),
    ADMIN_REMOVED("Admin Removed");

    private final String displayName;

    TransactionStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
