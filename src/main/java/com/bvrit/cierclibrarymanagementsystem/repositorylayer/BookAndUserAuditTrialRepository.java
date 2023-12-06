package com.bvrit.cierclibrarymanagementsystem.repositorylayer;

import com.bvrit.cierclibrarymanagementsystem.enums.BookAndUserAuditStatus;
import com.bvrit.cierclibrarymanagementsystem.models.Book;
import com.bvrit.cierclibrarymanagementsystem.models.BookAndUserAuditTrial;
import com.bvrit.cierclibrarymanagementsystem.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookAndUserAuditTrialRepository extends JpaRepository<BookAndUserAuditTrial, Integer> {
    BookAndUserAuditTrial findByBookAndCardAndStatus(Book book, Card card, BookAndUserAuditStatus status);
    List<BookAndUserAuditTrial> findByStatus(BookAndUserAuditStatus bookAndUserAuditStatus);
}
