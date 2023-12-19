package com.bvrit.cierclibrarymanagementsystem.controllerlayer;

import com.bvrit.cierclibrarymanagementsystem.enums.BookAndUserAuditStatus;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.BookAndUserAuditTrialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity returnBook(@RequestParam String userCode, @RequestParam String bookCode){
        try {
            return new ResponseEntity(bookAndUserAuditTrialService.returnBook(userCode, bookCode), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
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


}
