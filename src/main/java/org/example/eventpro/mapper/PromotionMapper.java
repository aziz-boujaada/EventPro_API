
package org.example.eventpro.mapper;

import org.example.eventpro.Domain.Event;
import org.example.eventpro.Domain.Promotion;
import org.example.eventpro.dto.CreatePromotionRequest;
import org.example.eventpro.dto.PromotionResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PromotionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "event", source = "event")
    @Mapping(target = "code", source = "dto.code")
    @Mapping(target = "discountType", source = "dto.discountType")
    @Mapping(target = "discountValue", source = "dto.discountValue")
    @Mapping(target = "startDate", source = "dto.startDate")
    @Mapping(target = "endDate", source = "dto.endDate")
    @Mapping(target = "usageLimit", source = "dto.usageLimit")
    @Mapping(target = "usageCount", constant = "0")
    @Mapping(target = "reservations", ignore = true)
    Promotion toEntity(
            CreatePromotionRequest dto,
            Event event
    );

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "event", source = "event")
    @Mapping(target = "code", source = "dto.code")
    @Mapping(target = "discountType", source = "dto.discountType")
    @Mapping(target = "discountValue", source = "dto.discountValue")
    @Mapping(target = "startDate", source = "dto.startDate")
    @Mapping(target = "endDate", source = "dto.endDate")
    @Mapping(target = "usageLimit", source = "dto.usageLimit")
    @Mapping(target = "usageCount", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    void update(
            CreatePromotionRequest dto,
            Event event,
            @MappingTarget Promotion promotion
    );

    @Mapping(target = "eventId", source = "event.id")
    PromotionResponseDto toResponse(Promotion promotion);
}
