package org.example.eventpro.dto.ReservationLine;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ReservationLineRequestDto(
        @NotNull(message = "L'identifiant de la catégorie est obligatoire")
        Long eventCategoryId,

        @NotNull(message = "La quantité est obligatoire")
        @Min(value = 1, message = "La quantité doit être au moins égale à 1")
        Integer quantity
) {
}
