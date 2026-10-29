
package org.example.eventpro.mapper;

import org.example.eventpro.Domain.Reservation;
import org.example.eventpro.dto.Reservation.ReservationResponseDto;
import org.example.eventpro.dto.Reservation.CreateReservationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.BeanMapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    // Request DTO -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reference", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "expirationDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "promotion", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "lines", ignore = true)
    @Mapping(target = "payments", ignore = true)
    Reservation toEntity(CreateReservationRequest request);

    // Entity -> Response DTO
    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "promotionId", source = "promotion.id")
    ReservationResponseDto toResponse(Reservation reservation);

    // Update existing Entity from Request DTO
    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reference", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "expirationDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "promotion", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "lines", ignore = true)
    @Mapping(target = "payments", ignore = true)
    void update(
            CreateReservationRequest request,
            @MappingTarget Reservation reservation
    );
}
