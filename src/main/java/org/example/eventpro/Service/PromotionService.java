package org.example.eventpro.Service;


import org.example.eventpro.Domain.Event;
import org.example.eventpro.Domain.Promotion;
import org.example.eventpro.Exception.DatesConflictsException;
import org.example.eventpro.Exception.ResourceAlreadyExistException;
import org.example.eventpro.Exception.ResourceNotFoundException;
import org.example.eventpro.Repository.EventRepository;
import org.example.eventpro.Repository.PromotionRepository;
import org.example.eventpro.dto.CreatePromotionRequest;
import org.example.eventpro.dto.PromotionResponseDto;
import org.example.eventpro.mapper.PromotionMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PromotionService {

    private final PromotionRepository promotionRepository;
    private final PromotionMapper promotionMapper;
    private final EventRepository eventRepository;

    public PromotionService(
            PromotionRepository promotionRepository,
            PromotionMapper promotionMapper,
            EventRepository eventRepository
    ) {
        this.promotionRepository = promotionRepository;
        this.promotionMapper = promotionMapper;
        this.eventRepository = eventRepository;
    }

    /// create promotion
    public PromotionResponseDto createPromotion(CreatePromotionRequest request) {

        Event event = getEvent(request.eventId());
        validateDates(request);

        /// check if promotion already exist in event
        if (promotionRepository.existsByEventIdAndCodeIgnoreCase(request.eventId(), request.code())) {
            // throwing the resource already exist
            throw new ResourceAlreadyExistException("A promotion with this code already exists for this event");
        }

        Promotion promotion = promotionMapper.toEntity(request, event);

        Promotion savedPromotion = promotionRepository.save(promotion);

        return promotionMapper.toResponse(savedPromotion);

    }

    /// update promotion
    public PromotionResponseDto updatePromotion(CreatePromotionRequest request, Long promotionId) {

        Promotion promotion = getPromotion(promotionId);
        Event event = getEvent(request.eventId());

        validateDates(request);

        /// check if promotion with given code already exist
        boolean duplicatePromotion = promotionRepository.existsByEventIdAndCodeIgnoreCaseAndIdNot(
                event.getId(),
                request.code(),
                promotionId
        );

        if (duplicatePromotion) {
            throw new ResourceAlreadyExistException("the promotion with this code already exist for this event ");
        }

        promotionMapper.update(request, event, promotion);

        Promotion updatedPromotion = promotionRepository.save(promotion);

        return promotionMapper.toResponse(updatedPromotion);


    }

    /// delete promotion
    public void delete(Long id) {
        Promotion promotion = getPromotion(id);

        if (!promotion.getReservations().isEmpty()) {
            throw new IllegalStateException(
                    "Cannot delete a promotion that has reservations"
            );
        }

        promotionRepository.delete(promotion);
    }

    /// validate dates
    public void validateDates(CreatePromotionRequest request) {
        LocalDate startDate = request.startDate();
        LocalDate endDate = request.endDate();

        if (startDate != null && endDate != null && endDate.isBefore(startDate)) {
            /// throwing conflicts exception
            throw new DatesConflictsException("End date must be after or equal to start date");
        }
    }

    /// get event
    public Event getEvent(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException
                        ("Event with id " + eventId + "Not found"));
    }

    /// get promotion
    public Promotion getPromotion(Long promotionId) {
        return promotionRepository.findById(promotionId)
                .orElseThrow(() -> new ResourceNotFoundException
                        ("Event with id " + promotionId + "Not found"));
    }

    /// get all promotions
    public List<PromotionResponseDto> getAllPromotions(){
        return promotionRepository.findAll()
                .stream()
                .map(promotionMapper::toResponse)
                .toList();
    }

    /// get by event id
    public List<PromotionResponseDto> getPromotionsByEventId(Long eventId){
        getEvent(eventId);
        return promotionRepository.findByEventId(eventId)
                .stream()
                .map(promotionMapper::toResponse)
                .toList();
    }
}
