package com.bvrit.cierclibrarymanagementsystem.enums;

public enum CardStatus {
    NEW ("Newly registered. Haven't issued any book"),
    ACTIVE ("Issued at least 1 book"),
    EXPIRED ("Passed out of the college"),
    BLOCKED ("Blocked the user");
    private final String info;
    CardStatus(String info){
        this.info=info;
    }
    public String getInfo(){
        return this.info;
    }
}
