package com.bvrit.cierclibrarymanagementsystem.generators;

import com.bvrit.cierclibrarymanagementsystem.repositorylayer.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class BookCodeGenerator extends CodeGenerator {
//    private static final String Prefix=null;
//    private static final String FORMAT_PATTERN="yyyy";
//    private static final Long MAX_SEQUENCE_NUMBER=Long.MAX_VALUE;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public String generate(String prefix) throws Exception {
        return super.generate(prefix);
    }

    @Override
    public Long getLatestSequenceNumber(String year) {
        Long latestSequenceNumber = bookRepository.findLatestSequenceNumber(year);
        return (latestSequenceNumber!=null) ? latestSequenceNumber : 0;
    }
}
