package com.bvrit.cierclibrarymanagementsystem.generators;

import com.bvrit.cierclibrarymanagementsystem.exceptions.BookDatabaseMaximumLimitReachedException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class CodeGenerator {
    private static final String Prefix=null;
    private static final String FORMAT_PATTERN="yyyy";
    private static final Long MAX_SEQUENCE_NUMBER=Long.MAX_VALUE;



    public String generate(String prefix) throws Exception{
        LocalDate currentDate=LocalDate.now();
        String year= currentDate.format(DateTimeFormatter.ofPattern(FORMAT_PATTERN));

        //fetching the latest sequence number that persisted previously
        long sequenceNumber = getLatestSequenceNumber(year);
        sequenceNumber+=1;
        if(sequenceNumber>MAX_SEQUENCE_NUMBER){
            throw new BookDatabaseMaximumLimitReachedException("Cannot Add book as it reached max limit to add books for this year "+year);
        }
        return year+prefix+String.format("%04d", sequenceNumber);
    }
    public Long getLatestSequenceNumber(String year){
        return Long.MAX_VALUE;
    }
}
