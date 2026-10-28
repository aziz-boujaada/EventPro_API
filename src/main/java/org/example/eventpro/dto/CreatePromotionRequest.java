package org.example.eventpro.dto;

import jakarta.validation.constraints.*;
import org.example.eventpro.Enums.DiscountType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreatePromotionRequest(
        @NotNull(message = "Event ID is required")
        @Positive(message = "Event ID must be positive")
        Long eventId,

        @NotBlank(message = "Promotion code is required")
        @Size(max = 50, message = "Code must not exceed 50 characters")
        String code,

        @NotNull(message = "Discount type is required")
        DiscountType discountType,

        @NotNull(message = "Discount value is required")
        @DecimalMin(value = "0.01", message = "Discount value must be greater than zero")
        BigDecimal discountValue,

        LocalDate startDate,

        LocalDate endDate,

        @Positive(message = "Usage limit must be positive")
        Integer usageLimit
) {
}
