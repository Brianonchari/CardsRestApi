package com.logicea.cardsrestapi.card.service.impl;

import com.logicea.cardsrestapi.card.dtos.requests.CardUpdateRequest;
import com.logicea.cardsrestapi.card.model.Card;
import com.logicea.cardsrestapi.card.repository.CardRepository;
import com.logicea.cardsrestapi.exception.UserNotFoundException;
import com.logicea.cardsrestapi.user.model.Role;
import com.logicea.cardsrestapi.user.model.User;
import com.logicea.cardsrestapi.user.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class CardServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CardServiceImpl cardService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testGetAllCards_AdminRole_ReturnsAllCards() {
        // Arrange
        User adminUser = new User();
        adminUser.setId(1L);
        adminUser.setRole(Role.ADMIN);

        List<Card> allCards = Arrays.asList(new Card(), new Card());

        when(userRepository.findById(adminUser.getId())).thenReturn(Optional.of(adminUser));
        when(cardRepository.findAll()).thenReturn(allCards);

        // Act
        List<Card> result = cardService.getAllCards(adminUser);

        // Assert
        assertEquals(allCards, result);
        verify(userRepository, times(1)).findById(adminUser.getId());
        verify(cardRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllCards_UserRole_ReturnsUserCards() {
        // Arrange
        User user = new User();
        user.setId(2L);
        user.setRole(Role.USER);

        List<Card> userCards = Arrays.asList(new Card(), new Card());

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(cardRepository.findAllByUser(user.getId())).thenReturn(userCards);

        // Act
        List<Card> result = cardService.getAllCards(user);

        // Assert
        assertEquals(userCards, result);
        verify(userRepository, times(1)).findById(user.getId());
        verify(cardRepository, times(1)).findAllByUser(user.getId());
    }


    @Test
    public void testGetAllCards_UnauthorizedRole_ThrowsException() {
        // Arrange
        User unauthorizedUser = new User();
        unauthorizedUser.setId(3L);
        unauthorizedUser.setRole(null);

        when(userRepository.findById(unauthorizedUser.getId())).thenReturn(Optional.of(unauthorizedUser));

        // Act and Assert
        try {
            cardService.getAllCards(unauthorizedUser);
            Assert.fail("Expected UserNotFoundException");
        } catch (UserNotFoundException e) {
            assertEquals(HttpStatus.UNAUTHORIZED, e.getStatus());
            assertEquals("You are unauthorized to retrieve this resource", e.getMessage());
        }
        verify(userRepository, times(1)).findById(unauthorizedUser.getId());
        verify(cardRepository, never()).findAll();
        verify(cardRepository, never()).findAllByUser(anyLong());
    }


    @Test
    public void testUpdateCard_ValidCardIdAndUserId_ReturnsSuccessMessage() {
        // Arrange
        Long cardId = 1L;
        Long userId = 2L;

        User authenticatedUser = new User();
        authenticatedUser.setId(userId);

        CardUpdateRequest cardUpdateRequest = new CardUpdateRequest();
        // Set the necessary properties of the cardUpdateRequest

        Card card = new Card();
        card.setId(cardId);
        // Set the necessary properties of the card

        Card updatedCard = new Card();
        updatedCard.setId(cardId);
        // Set the necessary properties of the updatedCard

        when(userRepository.findById(userId)).thenReturn(Optional.of(authenticatedUser));
        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));
        when(cardRepository.save(any(Card.class))).thenReturn(updatedCard);

        // Act
        String result = cardService.updateCard(cardId, userId, cardUpdateRequest);

        // Assert
        assertEquals("Card updated successfully", result);
        verify(userRepository, times(1)).findById(userId);
        verify(cardRepository, times(1)).findById(cardId);
        verify(cardRepository, times(1)).save(updatedCard);
    }

    @Test
    public void getCardById(){
        User user = new User();
        user.setId(1L);

        Card card = new Card();
        card.setId(1L);
        card.setCreatedBy(user);

        UserRepository userRepository = Mockito.mock(UserRepository.class);
        CardRepository cardRepository = Mockito.mock(CardRepository.class);

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(cardRepository.findById(1L)).thenReturn(Optional.of(card));

        CardServiceImpl cardService = new CardServiceImpl(cardRepository,userRepository);
        Card result = cardService.getCardById(1L, 1L);

        Assert.assertEquals(card, result);
    }
}