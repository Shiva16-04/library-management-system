package com.bvrit.cierclibrarymanagementsystem.servicelayer;

import com.bvrit.cierclibrarymanagementsystem.models.Card;
import com.bvrit.cierclibrarymanagementsystem.models.User;
import org.springframework.stereotype.Service;


public interface CardService {
    public Card createCard(User user);
}
