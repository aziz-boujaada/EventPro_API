package org.example.eventpro.mapper;

import org.example.eventpro.Domain.Event;
import org.example.eventpro.dto.CreateEventRequestDto;
import org.example.eventpro.dto.EventResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")

public interface EventMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "organizer", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "promotions", ignore = true)
    Event toEntity(CreateEventRequestDto request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "organizer", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "promotions", ignore = true)
    void updateEvent(CreateEventRequestDto request, @MappingTarget Event event);

    @Mapping(source = "organizer.id" , target = "organizerId")
    @Mapping(source = "organizer.name" , target = "organizerName")
    @Mapping(source = "status", target = "status")
    EventResponseDto toResponseDto(Event event);
}
