package org.example.eventpro.dto.Reservation;

import org.example.eventpro.Enums.ReservationStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record ReservationResponseDto (

    Long id,
    String reference,
    Long clientId,
    Instant creationDate,
    Instant expirationDate,
    ReservationStatus status,
    Long promotionId,
    BigDecimal totalAmount
    //List<ReservationLineResponseDto> lines
){}

