package org.example.eventpro.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TicketCategoriesPerEventRequestDto(

        @NotNull(message = "Event ID is required")
        @Min(value = 1, message = "Event ID must be positive")
        Long eventId,

        @NotNull(message = "Category ID is required")
        @Min(value = 1, message = "Category ID must be positive")
        Long categoryId,

        @NotNull(message = "Unit price is required")
        @DecimalMin(value = "0.0", message = "Unit price must be positive or zero")
        BigDecimal unitPrice,

        @NotNull(message = "Total quantity is required")
        @Min(value = 1, message = "Total quantity must be at least 1")
        Integer totalQuantity,

        @NotNull(message = "Maximum quota per client is required")
        @Min(value = 1, message = "Maximum quota per client must be at least 1")
        Integer maxQuotaPerClient

) {}