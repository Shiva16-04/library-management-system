package com.bvrit.cierclibrarymanagementsystem.Transformers;

import com.bvrit.cierclibrarymanagementsystem.enums.CardStatus;
import com.bvrit.cierclibrarymanagementsystem.models.Card;
import com.bvrit.cierclibrarymanagementsystem.models.User;

public class CardTransformer {
    public static Card cardRequestToCard(){
        return Card.builder()
                .status(CardStatus.NEW)
                .numberOfBooksIssued(0)
                .build();
    }
}
