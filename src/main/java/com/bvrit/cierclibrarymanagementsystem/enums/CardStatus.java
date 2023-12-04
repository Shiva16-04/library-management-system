package com.bvrit.cierclibrarymanagementsystem.enums;

public enum CardStatus {
    ACTIVE ("Issued at least 1 book"),
    BLOCKED ("Blocked the user. BlackListed User. Book cannot be issued for ever"),
    EXPIRED ("Passed out of the college / left the college"),
    FREEZE("Freeze the user. Book cannot be issued for the time being"),
    NEW ("Newly registered. Haven't issued any book");
    private final String info;
    CardStatus(String info){
        this.info=info;
    }
    public String getInfo(){
        return this.info;
    }
}
