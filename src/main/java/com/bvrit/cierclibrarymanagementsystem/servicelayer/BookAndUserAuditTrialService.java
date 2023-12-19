package com.bvrit.cierclibrarymanagementsystem.servicelayer;

import com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos.BookAndUserAuditTrialResponse;
import com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos.UserResponse;
import com.bvrit.cierclibrarymanagementsystem.enums.BookAndUserAuditStatus;

import java.util.List;


public interface BookAndUserAuditTrialService {
    public void updateFinePerDayAndMaxBooksIssuePerUser(int finePerDay, int maxBooksIssuePerUser);
    public String issueBook(String userCode, String bookCode)throws Exception;
    public String returnBook(String userCode, String bookCode)throws Exception;
    public int calculateFineAmount(String userCode, String bookCode, int additionalAmount)throws Exception;
    public List<BookAndUserAuditTrialResponse> getBookAndUserAuditTrialListByStatus(List<BookAndUserAuditStatus> bookAndUserAuditStatusList);
    public List<UserResponse> getActiveBookBorrowersList()throws Exception;
}
