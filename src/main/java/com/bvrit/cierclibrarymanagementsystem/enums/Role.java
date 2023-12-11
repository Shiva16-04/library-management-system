package com.bvrit.cierclibrarymanagementsystem.enums;

public enum Role {
    STUDENT("ROLE_STUDENT"),
    ADMIN("ROLE_ADMIN");
    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
