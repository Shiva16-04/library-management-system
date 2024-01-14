package com.bvrit.cierclibrarymanagementsystem.controllerlayer;

import com.bvrit.cierclibrarymanagementsystem.enums.BookAndUserAuditStatus;
import com.bvrit.cierclibrarymanagementsystem.enums.CardStatus;
import com.bvrit.cierclibrarymanagementsystem.enums.GeneralComments;
import com.bvrit.cierclibrarymanagementsystem.enums.ReturnItem;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.BookAndUserAuditTrialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("issue-return")
public class BookAndUserAuditTrialController {
    @Autowired
    private BookAndUserAuditTrialService bookAndUserAuditTrialService;

    @PostMapping("/issue-book")
    public ResponseEntity issueBook(@RequestParam String userCode, @RequestParam String bookCode){
        try {
            return new ResponseEntity<>(bookAndUserAuditTrialService.issueBook(userCode, bookCode), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/return-book")
    public ResponseEntity returnBook(@RequestParam String userCode, @RequestParam String bookCode, @RequestParam ReturnItem returnItem, @RequestParam GeneralComments comment){
        try {
            return new ResponseEntity(bookAndUserAuditTrialService.returnBook(userCode, bookCode, returnItem, comment), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/freeze-user")
    public ResponseEntity freezeUser(@RequestParam String userCode, @RequestParam String bookCode, @RequestParam GeneralComments comment){
        try {
            return new ResponseEntity<>(bookAndUserAuditTrialService.freezeOrUnFreeze(userCode, bookCode, comment, CardStatus.FREEZE, BookAndUserAuditStatus.ISSUED, BookAndUserAuditStatus.FREEZE), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/un-freeze-user")
    public ResponseEntity UnFreezeUser(@RequestParam String userCode, @RequestParam String bookCode, @RequestParam GeneralComments comment){
        try {
            return new ResponseEntity<>(bookAndUserAuditTrialService.freezeOrUnFreeze(userCode, bookCode, comment, CardStatus.ACTIVE, BookAndUserAuditStatus.FREEZE, BookAndUserAuditStatus.ISSUED), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/send-mail-to-book-overdue-borrowers")
    public ResponseEntity sendMailToBookOverdueBorrowers(){
        return new ResponseEntity("",HttpStatus.OK);
    }

    @GetMapping("/calculate-fine-amount")
    public ResponseEntity calculateFineAmount(@RequestParam String userCode, @RequestParam String bookCode, @RequestParam int additionalAmount){
        try {
            return new ResponseEntity(bookAndUserAuditTrialService.calculateFineAmount(userCode, bookCode, additionalAmount), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-active-book-borrowers-list")
    public ResponseEntity getActiveBookBorrowersList(){
        try {
            return new ResponseEntity(bookAndUserAuditTrialService.getActiveBookBorrowersList(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.OK);
        }
    }
    @GetMapping("/get-user-book-issue-return-record-list-by-status")
    public ResponseEntity getUserBookIssueReturnRecordListByStatus(@RequestParam List<BookAndUserAuditStatus> bookAndUserAuditStatusList){
        return new ResponseEntity(bookAndUserAuditTrialService.getBookAndUserAuditTrialListByStatus(bookAndUserAuditStatusList), HttpStatus.OK);
    }
    @GetMapping("/get-filtered-book-user-audit-trial-list")
    public ResponseEntity getAuditTrialFilteredList(@RequestParam(required = false) String issueReturnCode,
                                                    @RequestParam(required = false) String userCode,
                                                    @RequestParam(required = false) String bookCode,
                                                    @RequestParam(required = false) List<BookAndUserAuditStatus> statuses,
                                                    @RequestParam(required = false) List<ReturnItem> returnItems,
                                                    @RequestParam(required = false) LocalDate issueDateStart,
                                                    @RequestParam(required = false) LocalDate issueDateEnd,
                                                    @RequestParam(required = false) LocalDate returnDateStart,
                                                    @RequestParam(required = false) LocalDate returnDateEnd,
                                                    @RequestParam(required = false) LocalDate returnedOnStart,
                                                    @RequestParam(required = false) LocalDate returnedOnEnd,
                                                    @RequestParam(required = false) Boolean returnedOnIsNull){
        if ((returnedOnStart != null && returnedOnEnd == null)|| (returnedOnStart == null && returnedOnEnd != null) ) {
            throw new IllegalArgumentException("If 'returnedOnStart' is provided, 'returnedOnEnd' must also be provided.");
        }
        return new ResponseEntity<>(bookAndUserAuditTrialService.
                getAuditTrialFilteredList(issueReturnCode, userCode, bookCode, statuses, returnItems, issueDateStart,
                        issueDateEnd, returnDateStart, returnDateEnd, returnedOnStart, returnedOnEnd, returnedOnIsNull),
                HttpStatus.OK);
    }

}
