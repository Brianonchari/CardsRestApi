package com.logicea.cardsrestapi.card.repository;

import com.logicea.cardsrestapi.card.model.Card;
import com.logicea.cardsrestapi.card.model.CardStatus;
import com.logicea.cardsrestapi.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {


    @Query("SELECT c FROM Card c WHERE c.createdBy.id = :user " +
            "AND (:name IS NULL OR c.name LIKE %:name%) " +
            "AND (:color IS NULL OR c.color = :color) " +
            "AND (:status IS NULL OR c.status = :status) " +
            "AND (:startDate IS NULL OR c.createdAt >= :startDate) " +
            "AND (:endDate IS NULL OR c.createdAt <= :endDate)")
    Page<Card> searchCards(@Param("user") Long user,
                           @Param("name") String name,
                           @Param("color") String color,
                           @Param("status") CardStatus status,
                           @Param("startDate") LocalDateTime startDate,
                           @Param("endDate") LocalDateTime endDate,
                           Pageable pageable);

    @Query("SELECT c FROM Card  c where c.createdBy.id = :user")
    List<Card> findAllByUser(Long user);


    @Modifying
    @Query("UPDATE Card c SET c.name = :name, c.description = :description, c.color = :color, c.status = :status " +
            "WHERE c.id = :cardId AND c.createdBy.id = :userId")
    void updateCard(@Param("cardId") Long cardId,
                    @Param("userId") Long userId,
                    @Param("name") String name,
                    @Param("description") String description,
                    @Param("color") String color,
                    @Param("status") CardStatus status);
}
