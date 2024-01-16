package com.bvrit.cierclibrarymanagementsystem.repositorylayer;

import com.bvrit.cierclibrarymanagementsystem.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    @Query(value = "SELECT DISTINCT atr.* from Author atr " +
            "LEFT JOIN author_book_list abl ON atr.id = abl.author_list_id " +
            "LEFT JOIN book bk ON abl.book_list_id = bk.id " +
            "WHERE (:authorCode IS NULL OR atr.author_code = :authorCode) " +
            "AND (:bookCode IS NULL OR bk.book_code = :bookCode) " +
            "AND (:name IS NULL OR atr.name = :name) " +
            "AND ((:minAge IS NULL AND :maxAge IS NULL) OR (atr.age BETWEEN :minAge AND :maxAge)) " +
            "AND ((:minRating IS NULL AND :maxRating IS NULL) OR (atr.rating BETWEEN :minRating AND :maxRating)) " +
            "AND (:email IS NULL OR atr.email = :email) "
            , nativeQuery = true)
    List<Author>authorFilteredList(
            @Param("authorCode")String authorCode,
            @Param("bookCode")String bookCode,
            @Param("name")String name,
            @Param("minAge")Integer minAge,
            @Param("maxAge")Integer maxAge,
            @Param("minRating")Double minRating,
            @Param("maxRating")Double maxRating,
            @Param("email")String email
    );

    List<Author>findByAuthorCodeIn(List<String>authorCodeList);
    Optional<Author> findAuthorByEmail(String email);
    Optional<Author> findAuthorByAuthorCode(String authorCode);
    @Query(value = "SELECT MAX(CAST(SUBSTRING(author_code, 8) AS SIGNED)) FROM author WHERE SUBSTRING(author_code, 1, 4) = :year", nativeQuery = true)
    Long findLatestSequenceNumber(@Param("year") String year);
}
