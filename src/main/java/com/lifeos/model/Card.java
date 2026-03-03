package com.lifeos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "cards")
@Getter 
@Setter
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT") 
    private String description;

    
    @Enumerated(EnumType.STRING)
    private CardStatus status; 

    // --- TEMPORALIDAD AUTOMÁTICA ---
    private LocalDate startDate;
    private LocalDate endDate;

    // --- RELACIONES ESTRUCTURALES ---
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // --- RECURSIVIDAD (Padre/Hijo) ---
    @JsonIgnore 
    @ManyToOne
    @JoinColumn(name = "parent_card_id")
    private Card parentCard;

    @OneToMany(mappedBy = "parentCard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Card> subCards = new ArrayList<>();

    // --- TIPOS DINÁMICOS ---
    @JsonIgnore 
    @OneToMany(mappedBy = "definedInCard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CardType> definedTypes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "selected_option_id")
    private CardType selectedType;

    // --- LIFECYCLE HOOKS ---
    @PrePersist
    public void prePersist() {
        if (this.status == null) this.status = CardStatus.PENDING;
    }
}