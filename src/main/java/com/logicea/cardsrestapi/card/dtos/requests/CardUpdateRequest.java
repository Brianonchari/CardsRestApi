package com.logicea.cardsrestapi.card.dtos.requests;

import com.logicea.cardsrestapi.card.model.CardStatus;

public class CardUpdateRequest {
    private String name;
    private String description;
    private String color;
    private CardStatus cardStatus;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getColor() {
        return color;
    }

    public CardStatus getCardStatus() {
        return cardStatus;
    }
}
