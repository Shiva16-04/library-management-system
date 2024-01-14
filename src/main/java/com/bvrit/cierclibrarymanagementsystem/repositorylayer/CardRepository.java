package com.bvrit.cierclibrarymanagementsystem.repositorylayer;

import com.bvrit.cierclibrarymanagementsystem.enums.CardStatus;
import com.bvrit.cierclibrarymanagementsystem.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CardRepository extends JpaRepository<Card, Integer> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE card c SET c.status = :newStatus WHERE c.card_code = :userCode", nativeQuery = true)
    int updateCardStatusByCardCode(@Param("newStatus") String newStatus, @Param("userCode") String userCode);
}
