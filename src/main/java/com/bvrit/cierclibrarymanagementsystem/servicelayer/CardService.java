package com.bvrit.cierclibrarymanagementsystem.servicelayer;

import com.bvrit.cierclibrarymanagementsystem.enums.CardStatus;
import com.bvrit.cierclibrarymanagementsystem.models.Card;
import com.bvrit.cierclibrarymanagementsystem.models.User;

import java.util.List;


public interface CardService {
    public Card createCard(User user);
    public String updateUserCardStatus(List<String> userCodeList, CardStatus cardStatus)throws Exception;
}
