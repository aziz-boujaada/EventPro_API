package org.example.eventpro.Service;

import org.example.eventpro.Domain.*;
import org.example.eventpro.Enums.EventStatus;
import org.example.eventpro.Enums.ReservationStatus;
import org.example.eventpro.Enums.UserRole;
import org.example.eventpro.Exception.InvalidValueException;
import org.example.eventpro.Exception.ResourceNotFoundException;
import org.example.eventpro.Repository.EventCategoryRepository;
import org.example.eventpro.Repository.ReservationRepository;
import org.example.eventpro.Repository.TicketCategoryRepository;
import org.example.eventpro.Repository.UserRepository;
import org.example.eventpro.dto.Reservation.ReservationResponseDto;
import org.example.eventpro.dto.Reservation.CreateReservationRequest;
import org.example.eventpro.dto.ReservationLine.ReservationLineRequestDto;
import org.example.eventpro.mapper.ReservationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ReservationService {

    private final UserRepository userRepository;
    private final EventCategoryRepository eventCategoryRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    public ReservationService(
            UserRepository userRepository,
            EventCategoryRepository eventCategoryRepository,
            ReservationRepository reservationRepository,
            ReservationMapper reservationMapper
    ) {
        this.userRepository = userRepository;
        this.eventCategoryRepository = eventCategoryRepository;
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
    }

    @Transactional
    public ReservationResponseDto createReservation(CreateReservationRequest request) {
        // get client
        User user = getClient(request.clientId());
        if (!(user instanceof Client client)) {
            throw new InvalidValueException("you are not a client");
        }

        // set reservation
        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setReference("RES-" + UUID.randomUUID().toString());
        reservation.setStatus(ReservationStatus.PENDING_PAYMENT);
        reservation.setCreationDate(Instant.now());
        reservation.setExpirationDate(reservation.getCreationDate().plus(10, ChronoUnit.MINUTES));

        // get saved reservation lines
        List<ReservationLine> lines = saveReservationLines(request, reservation);

        // calcul total amount of reservation
        BigDecimal totalAmount = lines.stream()
                .map(ReservationLine::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        // set total amount
        reservation.setTotalAmount(totalAmount);

        // save and return reservation response
        Reservation savedReservation = reservationRepository.save(reservation);
        return reservationMapper.toResponse(savedReservation);

    }


    // 1. Get the authenticated client and verify it exists
    public User getClient(Long clientId) {
        return userRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client Not Found"));
    }

    // - get category
    public TicketCategoriesPerEvent getCategory(Long categoryId) {
        return eventCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category Not Found"));
    }

    // - get Event and check if open
    public Event getEvent(TicketCategoriesPerEvent category) {
        Event event = category.getEvent();
        if (event.getStatus() != EventStatus.PUBLISHED) {
            throw new InvalidValueException("event not published");
        }
        return event;
    }


    /// create and save Reservation lines
    public List<ReservationLine> saveReservationLines(CreateReservationRequest request, Reservation reservation) {

        List<ReservationLine> reservationLines = new ArrayList<>();

        for (ReservationLineRequestDto lineRequestDto : request.lines()) {
            // get categories from line request
            TicketCategoriesPerEvent categoriesPerEvent = getCategory(lineRequestDto.eventCategoryId());

            int quantity = lineRequestDto.quantity();

            // get available quantity of category
            int availableQuantity = categoriesPerEvent.getTotalQuantity();

            // check given  quantity from request
            if (quantity > availableQuantity) {
                throw new InvalidValueException("quantity must smaller or equals to total category quantity");
            }

            // compare the given quantity with max quota
            int maxQuota = categoriesPerEvent.getMaxQuotaPerClient();
            Integer quantityAlreadyReservedByClient =
                    eventCategoryRepository.sumQuantityByClientIdAndEventCategoryId(
                            request.clientId() ,
                            categoriesPerEvent.getId()
                    );

            if (quantityAlreadyReservedByClient + quantity > maxQuota) {
                throw new InvalidValueException("the max quota per client is " + categoriesPerEvent.getMaxQuotaPerClient());
            }

            // update category quantity
            categoriesPerEvent.setTotalQuantity(availableQuantity - quantity);
            eventCategoryRepository.save(categoriesPerEvent);

            /// calcul subtotal of lines ;
            BigDecimal subTotal = calculSubTotalOfLines(
                    categoriesPerEvent.getUnitPrice(),
                    quantity
            );


            // set lines of reservation
            ReservationLine line = new ReservationLine();
            line.setReservation(reservation);
            line.setCategoryPerEvent(categoriesPerEvent);
            line.setFixedUnitPrice(categoriesPerEvent.getUnitPrice());
            line.setSubtotal(subTotal);
            line.setQuantity(quantity);

            reservationLines.add(line);
            reservation.getLines().add(line);
        }
        return reservationLines;
    }

    public BigDecimal calculSubTotalOfLines(BigDecimal unitPrice, int quantity) {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }


}
