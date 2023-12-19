package com.bvrit.cierclibrarymanagementsystem.enums;

public enum TransactionStatus {

    //addition and deletion status enums
    ADMIN_ADDED("Admin Added"),
    ADMIN_REMOVED("Admin Removed"),
    AUTHOR_ADDED("Author Added"),
    AUTHOR_REMOVED("Author Removed"),
    BOOK_ADDED("Book Added"),
    BOOK_REMOVED("Book Removed"),
    USER_ADDED("User Added"),
    USER_REMOVED("User Removed"),

    //book transaction enums
    ISSUED("Issued"),
    PENDING("Pending"),
    RETURNED("Returned"),

    //user card status related enums
    ACTIVE ("Active"),
    BLOCKED ("Blocked"),
    EXPIRED ("Expired"),
    FREEZE("Freeze"),
    NEW ("New");
    private final String displayName;

    TransactionStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
