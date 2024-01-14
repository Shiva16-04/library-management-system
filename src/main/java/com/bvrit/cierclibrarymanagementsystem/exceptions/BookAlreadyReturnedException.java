package com.bvrit.cierclibrarymanagementsystem.exceptions;

public class BookAlreadyReturnedException extends Exception{
    public BookAlreadyReturnedException(String message) {
        super(message);
    }
}
