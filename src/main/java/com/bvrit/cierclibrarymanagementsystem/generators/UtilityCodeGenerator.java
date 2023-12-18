package com.bvrit.cierclibrarymanagementsystem.generators;

import com.bvrit.cierclibrarymanagementsystem.repositorylayer.UtilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UtilityCodeGenerator extends CodeGenerator{
    @Autowired
    private UtilityRepository utilityRepository;

    @Override
    public String generate(String prefix) throws Exception {
        return super.generate(prefix);
    }

    @Override
    public Long getLatestSequenceNumber(String year) {
        Long latestSequenceNumber = utilityRepository.findLatestSequenceNumber(year);
        return (latestSequenceNumber!=null) ? latestSequenceNumber : 0;
    }
}
