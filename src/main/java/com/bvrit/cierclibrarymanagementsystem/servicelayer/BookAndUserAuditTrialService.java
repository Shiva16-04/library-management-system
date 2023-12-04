package com.bvrit.cierclibrarymanagementsystem.servicelayer;

import org.springframework.stereotype.Service;


public interface BookAndUserAuditTrialService {
    public String issueBook(String userCode, String bookCode)throws Exception;
    public String returnBook(String userCode, String bookCode)throws Exception;
    public int fineAmount(String userCode, String bookCode)throws Exception;
}
