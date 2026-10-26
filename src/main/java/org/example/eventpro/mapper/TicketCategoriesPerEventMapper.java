package org.example.eventpro.mapper;



import org.example.eventpro.Domain.TicketCategoriesPerEvent;
import org.example.eventpro.dto.TicketCategoriesPerEventRequestDto;
import org.example.eventpro.dto.TicketCategoriesPerEventResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TicketCategoriesPerEventMapper {

    // Request DTO -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "event", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "reservationLines", ignore = true)
    TicketCategoriesPerEvent toEntity(
            TicketCategoriesPerEventRequestDto dto
    );

    // Update existing Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "event", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "reservationLines", ignore = true)
    void update(
            TicketCategoriesPerEventRequestDto dto,
            @MappingTarget TicketCategoriesPerEvent entity
    );

    // Entity -> Response DTO
    @Mapping(target = "eventId", source = "event.id")
    @Mapping(target = "categoryId", source = "category.id")
    TicketCategoriesPerEventResponseDto toResponse(
            TicketCategoriesPerEvent entity
    );
}

