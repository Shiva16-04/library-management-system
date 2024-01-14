package com.bvrit.cierclibrarymanagementsystem.enums;

public enum ReturnItem {
    DAMAGED_BOOK_AND_MONEY ("Damaged Book and Money"),
    MONEY("Money"),
    NEW_BOOK("New Book"),
    ORIGINAL_BOOK("Original Book"),
    NULL("Null");
    private final String displayName;
    ReturnItem(String displayName){
        this.displayName = displayName;
    }
}
