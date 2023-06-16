package com.logicea.cardsrestapi.card.controller;

import com.logicea.cardsrestapi.card.dtos.requests.CardRequest;
import com.logicea.cardsrestapi.card.dtos.requests.CardUpdateRequest;
import com.logicea.cardsrestapi.card.dtos.responses.CardResponse;
import com.logicea.cardsrestapi.card.dtos.responses.PagedResponse;
import com.logicea.cardsrestapi.card.model.Card;
import com.logicea.cardsrestapi.card.model.CardStatus;
import com.logicea.cardsrestapi.card.service.CardService;
import com.logicea.cardsrestapi.card.utils.Constants;
import com.logicea.cardsrestapi.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/cards")
@Slf4j
public class CardController {
    private final CardService cardService;
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    @PreAuthorize("#user.role == #user.role.ADMIN or #user.role == #user.role.USER")
    public ResponseEntity<?> getCards(
            @AuthenticationPrincipal User user
    ){
        return ResponseEntity.ok(cardService.getAllCards(user));
    }

    @PostMapping
    @PreAuthorize("#user.role == #user.role.ADMIN or #user.role == #user.role.USER")
    public ResponseEntity<?> createCard(@AuthenticationPrincipal User user, @RequestBody CardRequest cardRequest) {
        return ResponseEntity.ok(cardService.createCard(cardRequest, user));
    }

    @PutMapping("/{cardId}/users/{userId}")
    @PreAuthorize("#user.id == #userId")
    public ResponseEntity<?> updateCard(
            @AuthenticationPrincipal User user,
            @PathVariable Long cardId,
            @PathVariable Long userId,
            @RequestBody CardUpdateRequest cardUpdateRequest) {
        return ResponseEntity.ok(cardService.updateCard(cardId, userId, cardUpdateRequest));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchCards(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) CardStatus status,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(value = "page", required = false, defaultValue = Constants.DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(value = "size", required = false, defaultValue = Constants.DEFAULT_PAGE_SIZE) Integer size

    ) {

        PagedResponse<Card> responses = cardService.searchCards(user.getId(), name, color, status, startDate, endDate, page, size);

        log.error("Cards {}", responses);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{cardId}/users/{userId}")
    @PreAuthorize("#user.id == #userId")
    public ResponseEntity<?> getCardById(
            @AuthenticationPrincipal User user,
            @PathVariable Long userId,
            @PathVariable Long cardId
    ) {
        return ResponseEntity.ok(cardService.getCardById(cardId,userId));
    }

    @DeleteMapping("/{cardId}/users/{userId}")
    @PreAuthorize("#user.id == #userId")
    public ResponseEntity<?> deleteCard(
            @AuthenticationPrincipal User user,
            @PathVariable Long cardId,
            @PathVariable Long userId
    ) {
        cardService.deleteCard(cardId);
        return ResponseEntity.noContent().build();
    }

}
