package com.bvrit.cierclibrarymanagementsystem.generators;

import com.bvrit.cierclibrarymanagementsystem.repositorylayer.BookAndUserAuditTrialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookAndUserAuditTrialCodeGenerator extends CodeGenerator{
    @Autowired
    private BookAndUserAuditTrialRepository bookAndUserAuditTrialRepository;

    @Override
    public String generate(String prefix) throws Exception {
        return super.generate(prefix);
    }

    @Override
    public Long getLatestSequenceNumber(String year) {
        Long latestSequenceNumber = bookAndUserAuditTrialRepository.findLatestSequenceNumber(year);
        return (latestSequenceNumber!=null) ? latestSequenceNumber : 0;
    }
}
