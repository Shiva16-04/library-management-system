package com.bvrit.cierclibrarymanagementsystem.enums;

public enum Availability {
    AVAILABLE("Available"),
    NOT_AVAILABLE("Not Available");
    private final String displayName;
    Availability(String displayName){
        this.displayName=displayName;
    }
    public String getDisplayName(){
        return this.displayName;
    }
}
