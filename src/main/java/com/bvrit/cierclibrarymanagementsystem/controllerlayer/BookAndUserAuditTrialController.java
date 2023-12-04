package com.bvrit.cierclibrarymanagementsystem.controllerlayer;

import com.bvrit.cierclibrarymanagementsystem.servicelayer.BookAndUserAuditTrialService;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.impl.BookAndUserAuditTrialServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class BookAndUserAuditTrialController {
    @Autowired
    private BookAndUserAuditTrialServiceImpl bookAndUserAuditTrialServiceImpl;

    public ResponseEntity issueBook(@RequestParam String userCode, @RequestParam String bookCode){
        try {
            return new ResponseEntity<>(bookAndUserAuditTrialServiceImpl.issueBook(userCode, bookCode), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity returnBook(@RequestParam String userCode, @RequestParam String bookCode){
        try {
            return new ResponseEntity(bookAndUserAuditTrialServiceImpl.returnBook(userCode, bookCode), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
