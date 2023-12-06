package com.bvrit.cierclibrarymanagementsystem.controllerlayer;

import com.bvrit.cierclibrarymanagementsystem.enums.BookAndUserAuditStatus;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.BookAndUserAuditTrialService;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.impl.BookAndUserAuditTrialServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/calculate-fine-amount")
    public ResponseEntity calculateFineAmount(@RequestParam String userCode, @RequestParam String bookCode){
        try {
            return new ResponseEntity(bookAndUserAuditTrialService.calculateFineAmount(userCode, bookCode), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/get-active-book-borrowers-list")
    public ResponseEntity getActiveBookBorrowersList(){
        return new ResponseEntity(bookAndUserAuditTrialService.getActiveBookBorrowersList(), HttpStatus.OK);
    }
    @GetMapping("/get-user-book-issue-return-record-list")
    public ResponseEntity getUserBookIssueReturnRecordListByStatus(BookAndUserAuditStatus bookAndUserAuditStatus){
        return new ResponseEntity(bookAndUserAuditTrialService.getBookAndUserAuditTrialListByStatus(bookAndUserAuditStatus), HttpStatus.OK);
    }

}
