package com.bvrit.cierclibrarymanagementsystem.generators;

import com.bvrit.cierclibrarymanagementsystem.repositorylayer.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class AuthorCodeGenerator extends CodeGenerator{
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
