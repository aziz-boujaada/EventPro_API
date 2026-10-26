package org.example.eventpro.mapper;

import org.example.eventpro.Domain.Event;
import org.example.eventpro.Domain.TicketCategory;
import org.example.eventpro.dto.CreateCategoryRequest;
import org.example.eventpro.dto.CreateEventRequestDto;
import org.example.eventpro.dto.TicketCategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TicketCategoryMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "eventCategories" , ignore = true)

    TicketCategory toEntity(CreateCategoryRequest request);
    void updateEvent(CreateCategoryRequest request, @MappingTarget TicketCategory category);
    TicketCategoryResponse toResponse(TicketCategory category);
}
