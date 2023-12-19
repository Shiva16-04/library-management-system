package com.bvrit.cierclibrarymanagementsystem.repositorylayer;

import com.bvrit.cierclibrarymanagementsystem.enums.BookAndUserAuditStatus;
import com.bvrit.cierclibrarymanagementsystem.models.BookAndUserAuditTrial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookAndUserAuditTrialRepository extends JpaRepository<BookAndUserAuditTrial, Integer> {
    BookAndUserAuditTrial findByBookCodeAndCardCodeAndStatus(String bookCode, String cardCode, BookAndUserAuditStatus status);
    @Modifying
    @Query("UPDATE BookAndUserAuditTrial b SET b.status = :newStatus " +
            "WHERE b.bookCode = :bookCode " +
            "AND b.cardCode = :cardCode AND b.status = :bookAndUserAuditStatus")
    int updateBookAndUserAuditTrialStatusByBookCodeAndCardCodeAndStatus(@Param("newStatus") BookAndUserAuditStatus newStatus,
                                                                        @Param("bookCode") String bookCode,
                                                                        @Param("cardCode") String cardCode,
                                                                        @Param("bookAndUserAuditStatus")BookAndUserAuditStatus bookAndUserAuditStatus);
    List<BookAndUserAuditTrial> findByStatusIn(List<BookAndUserAuditStatus> bookAndUserAuditStatusList);
    List<BookAndUserAuditTrial> findByStatus(BookAndUserAuditStatus bookAndUserAuditStatus);
}
