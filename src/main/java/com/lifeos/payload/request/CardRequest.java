package com.lifeos.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDate;

import com.lifeos.model.CardStatus;

@Data
public class CardRequest {
    
    @NotBlank(message = "El título es obligatorio")
    private String title;
    
    private String description;
    
    private CardStatus status; // PENDING, IN_PROGRESS, DONE, ARCHIVED
    
    private LocalDate startDate;
    
    private LocalDate endDate;
    
    private Long categoryId; // EL DATO CLAVE
}
