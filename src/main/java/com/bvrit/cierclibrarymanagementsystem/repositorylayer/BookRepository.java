package com.bvrit.cierclibrarymanagementsystem.repositorylayer;

import com.bvrit.cierclibrarymanagementsystem.enums.BookStatus;
import com.bvrit.cierclibrarymanagementsystem.enums.Genre;
import com.bvrit.cierclibrarymanagementsystem.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findBookByBookCode(String bookCode);
    List<Book> findBookByName(String bookName);
    List<Book> findBookListByBookStatus(BookStatus bookStatus);
    List<Book> findBookListByGenre(Genre genre);
    @Query(value = "SELECT MAX(CAST(SUBSTRING(book_code, 7) AS SIGNED)) FROM book WHERE SUBSTRING(book_code, 1, 4) = :year", nativeQuery = true)
    Long findLatestSequenceNumber(@Param("year") String year);
    @Transactional
    @Modifying
    @Query("UPDATE Book b SET b.bookStatus = :newStatus WHERE b.bookCode = :bookCode")
    int updateBookStatusByBookCode(@Param("newStatus") BookStatus newStatus, @Param("bookCode") String bookCode);

}
