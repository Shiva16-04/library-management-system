package com.bvrit.cierclibrarymanagementsystem.enums;

public enum GeneralComments {
    BOOK_LOST ("Book Lost"),
    BOOK_DAMAGED ("Book Damaged"),
    ___ ("");
    private String displayName;
    GeneralComments(String displayName){
        this.displayName=displayName;
    }
    public String getDisplayName() {
        return displayName;
    }
}
