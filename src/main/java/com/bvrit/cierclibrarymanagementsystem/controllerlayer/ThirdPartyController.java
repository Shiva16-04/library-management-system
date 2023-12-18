package com.bvrit.cierclibrarymanagementsystem.controllerlayer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/third-party-controller")
public class ThirdPartyController {
    @GetMapping("/top-15-book-list-by-new-york-times")
    public ResponseEntity getTopBookList(){
        String url="https://api.nytimes.com/svc/books/v3/lists/current/hardcover-fiction.json?api-key=FobhPVJwn8be80doGep5u943AX80UnP4";
        RestTemplate restTemplate=new RestTemplate();
        Object restObject=restTemplate.getForObject(url,Object.class);
        return new ResponseEntity<>(restObject, HttpStatus.OK);
    }
}
