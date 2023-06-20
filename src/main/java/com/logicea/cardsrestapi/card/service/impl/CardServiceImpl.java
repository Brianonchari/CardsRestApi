package com.logicea.cardsrestapi.card.service.impl;

import com.logicea.cardsrestapi.card.dtos.requests.CardRequest;
import com.logicea.cardsrestapi.card.dtos.requests.CardUpdateRequest;
import com.logicea.cardsrestapi.card.dtos.responses.PagedResponse;
import com.logicea.cardsrestapi.card.factory.CardFactory;
import com.logicea.cardsrestapi.card.model.Card;
import com.logicea.cardsrestapi.user.model.Role;
import com.logicea.cardsrestapi.card.model.CardStatus;
import com.logicea.cardsrestapi.card.repository.CardRepository;
import com.logicea.cardsrestapi.card.service.CardService;
import com.logicea.cardsrestapi.exception.ApiResponse;
import com.logicea.cardsrestapi.exception.CardNotFoundException;
import com.logicea.cardsrestapi.exception.ForbiddenException;
import com.logicea.cardsrestapi.exception.UserNotFoundException;

import com.logicea.cardsrestapi.user.model.User;
import com.logicea.cardsrestapi.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    public CardServiceImpl(CardRepository cardRepository, UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

    /**
     * @param cardRequest
     * @param user
     * @return
     */
    @Override
    public ApiResponse createCard(CardRequest cardRequest, User user) {
        String color = cardRequest.getColor();
        // Validate color format
        if (color != null && !color.matches("#[a-zA-Z0-9]{6}")) {
            throw new IllegalArgumentException("Invalid color format. Color must start with '#' and be followed by 6 alphanumeric characters.");
        }
        User authenticatedUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND, "User not found with ID: " + user.getId()));

        Card card = CardFactory.createCard(cardRequest, authenticatedUser);
        cardRepository.save(card);
        return new ApiResponse(HttpStatus.CREATED.value(), "Card created successfully");
    }

    /**
     * @param user
     * @return
     */
    @Override
    public List<Card> getAllCards(User user) {
        User currentUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND, "User not found with ID: " + user.getId()));

        if (currentUser.getRole() == Role.ADMIN) {
            return cardRepository.findAll();
        } else if (currentUser.getRole() == Role.MEMBER) {
            return cardRepository.findAllByUser(currentUser.getId());
        } else {
            throw new UserNotFoundException(HttpStatus.UNAUTHORIZED, "You are unauthorized to retrieve this resource");
        }
    }

    @Override
    @Transactional
    public ApiResponse updateCard(Long cardId, Long userId, CardUpdateRequest cardUpdateRequest) {
        if (cardUpdateRequest.getCardStatus() != null) {
            switch (cardUpdateRequest.getCardStatus()) {
                case TODO:
                case IN_PROGRESS:
                case DONE:
                    break;
                default:
                    throw new IllegalArgumentException("Invalid card status");
            }
        }
        User authenticatedUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND, "User not found with ID: " + userId));

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException(HttpStatus.NOT_FOUND, "Card not found with ID: " + cardId));

        cardRepository.updateCard(
                card.getId(),
                authenticatedUser.getId(),
                cardUpdateRequest.getName(),
                cardUpdateRequest.getDescription(),
                cardUpdateRequest.getColor(),
                cardUpdateRequest.getCardStatus()
        );

        return new ApiResponse(HttpStatus.OK.value(), "Card updated successfully");
    }

    @Override
    public void deleteCard(Long cardId, User user) {
        User authenticatedUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND, "User not found with ID: " + user.getId()));
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException(HttpStatus.NOT_FOUND, "Card not found with id " + cardId));

        if (Objects.equals(authenticatedUser.getId(), card.getId())) {
            cardRepository.deleteById(cardId);
        } else {
            throw new ForbiddenException(HttpStatus.FORBIDDEN, "You are not authorized to delete this card");
        }
    }

    @Override
    public Card getCardById(Long cardId, Long userId) {
        User authenticatedUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND, "User not found with ID: " + userId));
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException(HttpStatus.NOT_FOUND, "Card not found with id:" + cardId));
        if (Objects.equals(authenticatedUser.getId(), card.getCreatedBy().getId())) {
            return card;
        } else {
            throw new ForbiddenException(HttpStatus.FORBIDDEN, "You are not authorized to access this resource");
        }
    }

    @Override
    public PagedResponse<Card> searchCards(Long userId, String nameFilter, String colorFilter,
                                           CardStatus statusFilter, LocalDateTime startDateFilter,
                                           LocalDateTime endDateFilter, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "name", "createdAt", "color", "status");
        Page<Card> cards = cardRepository.searchCards(userId, nameFilter, colorFilter, statusFilter, startDateFilter, endDateFilter, pageable);
        List<Card> content = cards.getNumberOfElements() == 0 ? Collections.emptyList() : cards.getContent();

        return new PagedResponse<>(content, cards.getNumber(), cards.getSize(), cards.getTotalElements(), cards.getTotalPages(), cards.isLast());
    }
}
