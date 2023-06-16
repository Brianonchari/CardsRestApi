package com.logicea.cardsrestapi.card.factory;

import com.logicea.cardsrestapi.card.dtos.requests.CardRequest;
import com.logicea.cardsrestapi.card.dtos.requests.CardUpdateRequest;
import com.logicea.cardsrestapi.card.model.Card;
import com.logicea.cardsrestapi.card.model.CardStatus;

import com.logicea.cardsrestapi.exception.InvalidCardStatusException;
import com.logicea.cardsrestapi.user.model.User;
import org.springframework.http.HttpStatus;

public class CardFactory {
    public static Card createCard(CardRequest cardRequest, User user){
        return new Card.CardBuilder(cardRequest.getName())
                .description(cardRequest.getDescription())
                .color(cardRequest.getColor())
                .user(user)
                .build();
    }

    public static Card updateCard(CardUpdateRequest cardUpdateRequest,User user,Card card){

        String name = cardUpdateRequest.getName() != null ? cardUpdateRequest.getName() : card.getName();
        String description = cardUpdateRequest.getDescription() != null ? cardUpdateRequest.getDescription() : card.getDescription();
        String color = cardUpdateRequest.getColor() != null ? cardUpdateRequest.getColor() : card.getColor();
        CardStatus status = cardUpdateRequest.getCardStatus() != null ? cardUpdateRequest.getCardStatus() : card.getStatus();

        // Validate and set the status
        if (status != null) {
            switch (status) {
                case TODO:
                case IN_PROGRESS:
                case DONE:
                    break;
                default:
                    throw new InvalidCardStatusException(HttpStatus.BAD_REQUEST,"Invalid card status");
            }
        }
        return new Card.CardBuilder(name)
                .description(description)
                .color(color)
                .user(user)
                .status(status)
                .build();
    }
}
