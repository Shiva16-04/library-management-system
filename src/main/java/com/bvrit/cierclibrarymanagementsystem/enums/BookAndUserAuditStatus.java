package com.bvrit.cierclibrarymanagementsystem.enums;

public enum BookAndUserAuditStatus {
    ISSUED("Issued the book"),
    RETURNED("Book Returned by the user successfully"),
    PENDING("Deadline crossed to return the issued book"),
    FREEZE("Freeze the transaction as book is lost/ damaged"),
    FAILURE("Server issue");
    private final String info;
    BookAndUserAuditStatus(String info){
        this.info=info;
    }
    public String getInfo(){
        return this.info;
    }
}
