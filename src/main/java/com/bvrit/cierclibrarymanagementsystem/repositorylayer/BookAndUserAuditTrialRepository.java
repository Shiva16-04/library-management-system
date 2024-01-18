package com.bvrit.cierclibrarymanagementsystem.repositorylayer;

import com.bvrit.cierclibrarymanagementsystem.enums.BookAndUserAuditStatus;
import com.bvrit.cierclibrarymanagementsystem.models.BookAndUserAuditTrial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookAndUserAuditTrialRepository extends JpaRepository<BookAndUserAuditTrial, Integer> {

    @Query(value = "SELECT * FROM book_and_user_audit_trial b " +
            "WHERE (:issueReturnCode IS NULL OR b.issue_return_code = :issueReturnCode) " +
            "AND (:userCode IS NULL OR b.card_code = :userCode )" +
            "AND (:bookCode IS NULL OR b.book_code = :bookCode) " +
            "AND (b.status IN (:statusList)) " +
            "AND (b.return_item IN (:returnItemList)) " +
            "AND ((:issueDateStart IS NULL AND :issueDateEnd IS NULL) OR (b.issue_date BETWEEN :issueDateStart AND :issueDateEnd)) " +
            "AND ((:returnDateStart IS NULL AND :returnDateEnd IS NULL) OR (b.return_date BETWEEN :returnDateStart AND :returnDateEnd)) " +
            "AND (((:returnedOnStart IS NULL AND :returnedOnEnd IS NULL) AND (:returnedOnIsNull IS NULL OR b.returned_on IS NULL = :returnedOnIsNull)) " +
            "OR (b.returned_on BETWEEN :returnedOnStart AND :returnedOnEnd)) " +
            "ORDER BY b.issue_date DESC", nativeQuery = true)
    List<BookAndUserAuditTrial> bookAndUserAuditTrialFilteredList(
            @Param("issueReturnCode") String issueReturnCode,
            @Param("userCode") String userCode,
            @Param("bookCode") String bookCode,
            @Param("statusList") List<String> statusList,
            @Param("returnItemList")List<String> returnItemList,
            @Param("issueDateStart")LocalDate issueDateStart,
            @Param("issueDateEnd")LocalDate issueDateEnd,
            @Param("returnDateStart")LocalDate returnDateStart,
            @Param("returnDateEnd")LocalDate returnDateEnd,
            @Param("returnedOnStart") LocalDate returnedOnStart,
            @Param("returnedOnEnd") LocalDate returnedOnEnd,
            @Param("returnedOnIsNull") Boolean returnedOnIsNull
    );
    @Modifying
    @Query(value = " UPDATE book_and_user_audit_trial b SET b.status = :newStatus WHERE b.issue_return_code = :issueReturnCode", nativeQuery = true)
    int updateBookAndUserAuditTrialStatusByIssueReturnCodeAndStatus(@Param("issueReturnCode")String issueReturnCode, @Param("newStatus") String newStatus);

    @Query(value = "SELECT MAX(CAST(SUBSTRING(issue_return_code, 7) AS SIGNED)) FROM book_and_user_audit_trial WHERE SUBSTRING(issue_return_code, 1, 4) = :year", nativeQuery = true)
    Long findLatestSequenceNumber(@Param("year") String year);


}
