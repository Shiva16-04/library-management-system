package com.bvrit.cierclibrarymanagementsystem.enums;

public enum Role {
    STUDENT("Student"),
    ADMIN("Admin");
    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
