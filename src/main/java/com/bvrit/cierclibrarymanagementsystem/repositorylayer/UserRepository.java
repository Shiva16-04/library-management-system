package com.bvrit.cierclibrarymanagementsystem.repositorylayer;

import com.bvrit.cierclibrarymanagementsystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT DISTINCT usr.* from user usr " +
            "LEFT JOIN card crd ON usr.id = crd.user_id " +
            "LEFT JOIN book bk ON crd.id = bk.card_id " +
            "WHERE (:userCode IS NULL OR usr.user_code = :userCode) " +
            "AND (:userName IS NULL OR usr.user_name = :userName) " +
            "AND (usr.role IN (:roleList)) " +
            "AND (:bloodGroup IS NULL OR usr.blood_group = :bloodGroup) " +
            "AND (:contactNumber IS NULL OR usr.contact_number = :contactNumber) " +
            "AND (:email IS NULL OR usr.email = :email) " +
            "AND ((:minFineAmount IS NULL AND :maxFineAmount IS NULL) OR (crd.fine_amount BETWEEN :minFineAmount AND :maxFineAmount)) " +
            "AND (crd.status IN (:cardStatusList)) " +
            "AND (:numberOfBooksIssued IS NULL OR crd.number_of_books_issued = :numberOfBooksIssued) " +
            "AND (:bookCode IS NULL OR bk.book_code = :bookCode) " +
            "AND (:bookName IS NULL OR bk.name = :bookName) "
            , nativeQuery = true)
    List<User> getFilteredUserList(
            @Param("userCode")String userCode,
            @Param("userName")String userName,
            @Param("roleList")List<String>roleList,
            @Param("bloodGroup")String bloodGroup,
            @Param("contactNumber")String contactNumber,
            @Param("email")String email,
            @Param("minFineAmount")Integer minFineAmount,
            @Param("maxFineAmount")Integer maxFineAmount,
            @Param("cardStatusList")List<String>cardStatusList,
            @Param("numberOfBooksIssued")Integer numberOfBooksIssued,
            @Param("bookCode")String bookCode,
            @Param("bookName")String bookName
    );
}
