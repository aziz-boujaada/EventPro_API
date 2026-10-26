package org.example.eventpro.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class CreateEventRequestDto {

    @NotBlank(message = "Le titre est obligatoire")
    @Size(max = 255, message = "Le titre ne doit pas dépasser 255 caractères")
    private String title;

    @Size(max = 4000, message = "La description ne doit pas dépasser 4000 caractères")
    private String description;

    @NotBlank(message = "Le lieu est obligatoire")
    @Size(max = 255, message = "Le lieu ne doit pas dépasser 255 caractères")
    private String location;

    @NotNull(message = "La date de début est obligatoire")
    @FutureOrPresent(message = "La date de début doit être aujourd'hui ou dans le futur")
    private LocalDate startDate;

    @NotNull(message = "La date de fin est obligatoire")
    private LocalDate endDate;

    @NotNull(message = "La date d'ouverture des ventes est obligatoire")
    private LocalDateTime salesOpeningDate;

    @NotNull(message = "La date de fermeture des ventes est obligatoire")
    private LocalDateTime salesClosingDate;

    @NotNull(message = "La capacité est obligatoire")
    @Positive(message = "La capacité doit être supérieure à zéro")
    private Integer capacity;
}