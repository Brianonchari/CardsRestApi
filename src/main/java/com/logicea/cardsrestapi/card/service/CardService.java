package com.logicea.cardsrestapi.card.service;

import com.logicea.cardsrestapi.card.dtos.requests.CardRequest;
import com.logicea.cardsrestapi.card.dtos.requests.CardUpdateRequest;
import com.logicea.cardsrestapi.card.dtos.responses.CardResponse;
import com.logicea.cardsrestapi.card.dtos.responses.PagedResponse;
import com.logicea.cardsrestapi.card.model.Card;
import com.logicea.cardsrestapi.card.model.CardStatus;
import com.logicea.cardsrestapi.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.time.LocalDateTime;
import java.util.List;

public interface CardService {


    Card createCard(CardRequest cardRequest, User user);
    List<Card> getAllCards(User user);

    String updateCard(Long cardId,Long userId, CardUpdateRequest cardUpdateRequest);

    void  deleteCard(Long cardId);

    Card getCardById(Long cardId,Long userId);

    PagedResponse<Card> searchCards(Long userId, String nameFilter, String colorFilter, CardStatus statusFilter,
                                    LocalDateTime startDateFilter, LocalDateTime endDateFilter, int page, int size);


}
