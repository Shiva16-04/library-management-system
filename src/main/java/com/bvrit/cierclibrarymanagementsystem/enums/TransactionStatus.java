package com.bvrit.cierclibrarymanagementsystem.enums;

public enum TransactionStatus {
    ISSUED("Issued the book"),
    RETURNED("Book Returned by the user successfully"),
    PENDING("Deadline crossed to return the issued book"),
    FAILURE("Book Lost or server issue");
    private final String info;
    TransactionStatus(String info){
        this.info=info;
    }
    public String getInfo(){
        return this.info;
    }
}
