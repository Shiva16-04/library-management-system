package com.bvrit.cierclibrarymanagementsystem.repositorylayer;

import com.bvrit.cierclibrarymanagementsystem.enums.BookStatus;
import com.bvrit.cierclibrarymanagementsystem.models.Author;
import com.bvrit.cierclibrarymanagementsystem.models.Book;
import com.bvrit.cierclibrarymanagementsystem.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query(value = "SELECT DISTINCT bk.* from Book bk " +
            "LEFT JOIN author_book_list abl ON bk.id = abl.book_list_id " +
            "LEFT JOIN author atr ON abl.author_list_id = atr.id " +
            "LEFT JOIN book_genre_list bgl ON bk.id = bgl.book_list_id " +
            "LEFT JOIN genre g ON bgl.genre_list_id = g.id " +
            "WHERE (:bookCode IS NULL OR bk.book_code = :bookCode) " +
            "AND (:authorCode IS NULL OR atr.author_code = :authorCode) " +
            "AND (:name IS NULL OR atr.name = :name) " +
            "AND (:readTime IS NULL OR bk.read_time = :readTime) " +
            "AND (g.name IN (:genreList)) " +
            "AND (bk.book_status IN (:statusList)) " +
            "AND ((:minRating IS NULL AND :maxRating IS NULL) OR (bk.rating BETWEEN :minRating AND :maxRating)) " +
            "AND ((:minPages IS NULL AND :maxPages IS NULL) OR (bk.no_of_pages BETWEEN :minPages AND :maxPages)) " +
            "AND ((:minPrice IS NULL AND :maxPrice IS NULL) OR (bk.price BETWEEN :minPrice AND :maxPrice)) "
            , nativeQuery = true)
    List<Book>findBooksByFilteredBookList(
            @Param("bookCode")String bookCode,
            @Param("authorCode")String authorCode,
            @Param("name")String name,
            @Param("readTime")Integer readTime,
            @Param("genreList") List<String> genreList,
            @Param("statusList") List<String> statusList,
            @Param("minRating") Double minRating,
            @Param("maxRating") Double maxRating,
            @Param("minPages") Integer minPages,
            @Param("maxPages") Integer maxPages,
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice

    );
    Optional<Book> findBookByBookCode(String bookCode);
    List<Book> findBookByName(String bookName);
    String findBookNameByBookCode(String bookCode);
    List<Book> findBookListByBookStatus(BookStatus bookStatus);
    List<Book> findBookListByGenreListIn(List<Genre> genreList);
    Optional<BookStatus> findByBookCode(String bookCode);
//    void deleteByBookCode(String bookCode);
    @Query(value = "SELECT MAX(CAST(SUBSTRING(book_code, 7) AS SIGNED)) FROM book WHERE SUBSTRING(book_code, 1, 4) = :year", nativeQuery = true)
    Long findLatestSequenceNumber(@Param("year") String year);
    @Transactional
    @Modifying
    @Query("UPDATE Book b SET b.bookStatus = :newStatus WHERE b.bookCode = :bookCode")
    int updateBookStatusByBookCode(@Param("newStatus") BookStatus newStatus, @Param("bookCode") String bookCode);

}
