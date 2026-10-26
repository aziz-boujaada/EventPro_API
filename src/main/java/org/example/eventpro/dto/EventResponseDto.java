package org.example.eventpro.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class EventResponseDto {

    private Long id;
    private Long organizerId;
    private String organizerName;
    private String title;
    private String description;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime salesOpeningDate;
    private LocalDateTime salesClosingDate;
    private String status;
    private Integer capacity;
}