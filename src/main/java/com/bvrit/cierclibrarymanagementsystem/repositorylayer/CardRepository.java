package com.bvrit.cierclibrarymanagementsystem.repositorylayer;

import com.bvrit.cierclibrarymanagementsystem.enums.CardStatus;
import com.bvrit.cierclibrarymanagementsystem.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CardRepository extends JpaRepository<Card, Integer> {
    @Modifying
    @Query("UPDATE Card c SET c.status = :newStatus WHERE c.user.userCode = :userCode")
    int updateCardStatusByUserCode(@Param("newStatus") CardStatus newStatus, @Param("userCode") String userCode);
}
