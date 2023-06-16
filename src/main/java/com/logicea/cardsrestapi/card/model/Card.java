package com.logicea.cardsrestapi.card.model;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.logicea.cardsrestapi.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private  String name;
    private  String description;

    private  String color;
    @Enumerated(EnumType.STRING)
    private CardStatus status;
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User createdBy;

    public Card(CardBuilder builder){
        this.name = builder.name;
        this.description = builder.description;
        this.color = builder.color;
        this.status = builder.status;
        this.createdAt = builder.createdAt;
        this.createdBy = builder.user;
    }
    public Card(){
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public CardStatus getStatus() {
        return status;
    }

    public void setStatus(CardStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

    public static class CardBuilder{

        private final String name;
        private String description;
        private String color;
        @Enumerated(EnumType.STRING)
        private CardStatus status;
        private LocalDateTime createdAt;

        private User user;


        public  CardBuilder(String name){
            this.name = name;
            this.status = CardStatus.TODO;
            this.createdAt = LocalDateTime.now();
        }

        public CardBuilder description(String description){
            this.description = description;
            return this;
        }

        public CardBuilder color(String color){
            this.color = color;
            return this;
        }

        public CardBuilder user(User user){
            this.user = user;
            return this;
        }

        public CardBuilder status(CardStatus status){
            this.status = status;
            return this;
        }

        public Card build(){
            Card card = new Card(this);
            return card;
        }
    }
}
