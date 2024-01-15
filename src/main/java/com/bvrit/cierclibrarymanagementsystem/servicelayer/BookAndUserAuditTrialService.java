package com.bvrit.cierclibrarymanagementsystem.servicelayer;

import com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos.BookAndUserAuditTrialResponse;
import com.bvrit.cierclibrarymanagementsystem.dtos.responsedtos.UserResponse;
import com.bvrit.cierclibrarymanagementsystem.enums.BookAndUserAuditStatus;
import com.bvrit.cierclibrarymanagementsystem.enums.CardStatus;
import com.bvrit.cierclibrarymanagementsystem.enums.GeneralComments;
import com.bvrit.cierclibrarymanagementsystem.enums.ReturnItem;

import java.time.LocalDate;
import java.util.List;


public interface BookAndUserAuditTrialService {
    public List<BookAndUserAuditTrialResponse> getAuditTrialFilteredList(String issueReturnCode, String userCode, String bookCode, List<BookAndUserAuditStatus> statuses,
                                                                         List<ReturnItem> returnItems, LocalDate issueDateStart, LocalDate issueDateEnd, LocalDate returnDateStart, LocalDate returnDateEnd,
                                                                         LocalDate returnedOnStart, LocalDate returnedOnEnd,  Boolean returnedOnIsNull);
    public void updateFinePerDayAndMaxBooksIssuePerUser(int finePerDay, int maxBooksIssuePerUser);
    public String issueBook(String userCode, String bookCode)throws Exception;
    public String returnBook(String issueReturnCode, ReturnItem returnItem, GeneralComments comment)throws Exception;
    public String sendMailToBookOverdueBorrowers()throws Exception;
    public int calculateFineAmount(String issueReturnCode, int additionalAmount)throws Exception;
    public List<UserResponse> getActiveBookBorrowersList()throws Exception;
    public String freezeOrUnFreeze(String issueReturnCode, GeneralComments comment, CardStatus newCardStatus, BookAndUserAuditStatus newStatus)throws Exception;
    public String updateStatusByBookAndUserCodeAndStatus(String issueReturnCode, BookAndUserAuditStatus newStatus)throws Exception;
}
