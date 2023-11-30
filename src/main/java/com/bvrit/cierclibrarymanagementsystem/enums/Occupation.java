package com.bvrit.cierclibrarymanagementsystem.enums;

public enum Occupation {
    STUDENT("Student"),
    LECTURER("Lecturer");
    private final String displayName;
    Occupation(String displayName){
        this.displayName=displayName;
    }
    public String getDisplayName(){
        return this.displayName;
    }
}
