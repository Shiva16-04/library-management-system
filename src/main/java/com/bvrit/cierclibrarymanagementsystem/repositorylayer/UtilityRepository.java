package com.bvrit.cierclibrarymanagementsystem.repositorylayer;

import com.bvrit.cierclibrarymanagementsystem.models.Utility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UtilityRepository extends JpaRepository<Utility, Integer> {
    Optional<Utility> findBySenderEmail(String senderEmail);
    Optional<Utility> findByUtilityCode(String utilityCode);
    @Query(value = "SELECT MAX(CAST(SUBSTRING(author_code, 8) AS SIGNED)) FROM author WHERE SUBSTRING(author_code, 1, 4) = :year", nativeQuery = true)
    Long findLatestSequenceNumber(@Param("year") String year);
}
