package org.example.eventpro.dto;

import org.example.eventpro.Enums.DiscountType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PromotionResponseDto(
        Long id,
        Long eventId,
        String code,
        DiscountType discountType,
        BigDecimal discountValue,
        LocalDate startDate,
        LocalDate endDate,
        Integer usageLimit,
        Integer usageCount
) {
}
