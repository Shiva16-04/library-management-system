package com.bvrit.cierclibrarymanagementsystem.generators;

import com.bvrit.cierclibrarymanagementsystem.repositorylayer.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class AuthorCodeGenerator extends CodeGenerator{
//    private static final String Prefix=null;
//    private static final String FORMAT_PATTERN="yyyy";
//    private static final Long MAX_SEQUENCE_NUMBER=Long.MAX_VALUE;

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public String generate(String prefix) throws Exception {
        return super.generate(prefix);
    }

    @Override
    public Long getLatestSequenceNumber(String year) {
        Long latestSequenceNumber = authorRepository.findLatestSequenceNumber(year);
        return (latestSequenceNumber!=null) ? latestSequenceNumber : 0;
    }
}
