package com.logicea.cardsrestapi.card.dtos.responses;

import com.logicea.cardsrestapi.card.model.CardStatus;

public class CardResponse {

    private Long id;
    private String name;
    private String description;
    private String color;
    private CardStatus status;
    private String createdAt;

    public CardResponse(Long id, String name,
                        String description,
                        String color, CardStatus status, String createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.color = color;
        this.status = status;
        this.createdAt = createdAt;
    }

    public CardResponse() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setStatus(CardStatus status) {
        this.status = status;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
