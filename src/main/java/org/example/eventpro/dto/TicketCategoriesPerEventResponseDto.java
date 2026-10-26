package org.example.eventpro.dto;

import java.math.BigDecimal;

public record TicketCategoriesPerEventResponseDto(
        Long id,
        Long eventId,
        Long categoryId,
        BigDecimal unitPrice,
        Integer totalQuantity,
        Integer maxQuotaPerClient) {
}
