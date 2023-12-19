package com.bvrit.cierclibrarymanagementsystem.servicelayer.impl;

import com.bvrit.cierclibrarymanagementsystem.Transformers.CardTransformer;
import com.bvrit.cierclibrarymanagementsystem.enums.CardStatus;
import com.bvrit.cierclibrarymanagementsystem.models.Card;
import com.bvrit.cierclibrarymanagementsystem.models.User;
import com.bvrit.cierclibrarymanagementsystem.repositorylayer.CardRepository;
import com.bvrit.cierclibrarymanagementsystem.servicelayer.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CardServiceImpl implements CardService {
    @Autowired
    private CardRepository cardRepository;

    public Card createCard(User user){
        Card card= CardTransformer.cardRequestToCard();

        //setting the attribute cardId by extracting local part of email
        String email= user.getEmail();
        card.setCardCode(email.substring(0,email.indexOf('@')));

        //setting foreign keys
        card.setUser(user);
        //bidirectionally mapping
        user.setCard(card);

        return card;
    }
    @Transactional
    public String updateUserCardStatus(List<String> userCodeList, CardStatus cardStatus){
        for(String userCode:userCodeList){
            cardRepository.updateCardStatusByUserCode(cardStatus, userCode);
        }
        return "Card status changed successfully";
    }
}
