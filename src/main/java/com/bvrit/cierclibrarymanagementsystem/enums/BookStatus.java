package com.bvrit.cierclibrarymanagementsystem.enums;

public enum BookStatus {
    AVAILABLE("Available"),
    IN_CIRCULATION("In Circulation"),
    NOT_AVAILABLE("Not Available");
    private final String displayName;
    BookStatus(String displayName){
        this.displayName=displayName;
    }
    public String getDisplayName(){
        return this.displayName;
    }
}
