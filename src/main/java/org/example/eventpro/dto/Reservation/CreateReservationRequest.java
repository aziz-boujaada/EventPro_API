package org.example.eventpro.dto.Reservation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.example.eventpro.dto.ReservationLine.ReservationLineRequestDto;


import java.util.List;

public record CreateReservationRequest(
        @NotNull(message = "Le client est obligatoire")
        @Positive
        Long clientId,

         @NotEmpty(message = "La réservation doit contenir au moins une ligne")
         List<@Valid ReservationLineRequestDto> lines
) {
}
