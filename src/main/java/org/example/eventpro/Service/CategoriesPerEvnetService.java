package org.example.eventpro.Service;

import org.example.eventpro.Domain.Event;
import org.example.eventpro.Domain.TicketCategoriesPerEvent;
import org.example.eventpro.Domain.TicketCategory;
import org.example.eventpro.Exception.InvalidValueException;
import org.example.eventpro.Exception.ResourceAlreadyExistException;
import org.example.eventpro.Exception.ResourceNotFoundException;
import org.example.eventpro.Repository.EventCategoryRepository;
import org.example.eventpro.Repository.EventRepository;
import org.example.eventpro.Repository.TicketCategoryRepository;
import org.example.eventpro.dto.TicketCategoriesPerEventRequestDto;
import org.example.eventpro.dto.TicketCategoriesPerEventResponseDto;
import org.example.eventpro.mapper.TicketCategoriesPerEventMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriesPerEvnetService {

    private final EventCategoryRepository eventCategoryRepository;
    private final EventRepository eventRepository;
    private final TicketCategoriesPerEventMapper categoriesPerEventMapper;
    private final TicketCategoryRepository ticketCategoryRepository;

    public CategoriesPerEvnetService(
            EventCategoryRepository eventCategoryRepository,
            TicketCategoriesPerEventMapper categoriesPerEventMapper,
            EventRepository eventRepository,
            TicketCategoryRepository ticketCategoryRepository) {
        this.eventCategoryRepository = eventCategoryRepository;
        this.categoriesPerEventMapper = categoriesPerEventMapper;
        this.eventRepository = eventRepository;
        this.ticketCategoryRepository = ticketCategoryRepository;
    }

    @Transactional
    public TicketCategoriesPerEventResponseDto createEventCategory(TicketCategoriesPerEventRequestDto request) {

        Event event = eventRepository.findByIdForUpdate(request.eventId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not Fount "));

        TicketCategory category = ticketCategoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        // check if category already exist in event
        if(eventCategoryRepository.existsByEventIdAndCategoryId(event.getId(), category.getId())){
            throw new ResourceAlreadyExistException("this category already linked to this event");
        }
        List<TicketCategoriesPerEventResponseDto> categories = findCategoriesByEventId(event.getId());

        calculCategoriesQuantity(
                event,
                categories,
                request.totalQuantity());


        // CategoriesPerEvnet to Entity
        TicketCategoriesPerEvent categoriesPerEvent = categoriesPerEventMapper.toEntity(request);

        categoriesPerEvent.setCategory(category);
        categoriesPerEvent.setEvent(event);

        return categoriesPerEventMapper.toResponse(eventCategoryRepository.save(categoriesPerEvent));

    }

    // get Categories of event
    public List<TicketCategoriesPerEventResponseDto> findCategoriesByEventId(Long eventId) {
        return eventCategoryRepository.findByEventId(eventId)
                .stream()
                .map(categoriesPerEventMapper::toResponse)
                .toList();
    }


    public void calculCategoriesQuantity(Event event, List<TicketCategoriesPerEventResponseDto> categories, int categoryQuantity) {
        int capacity = event.getCapacity();

        int totalCategoriesQuantity = categories.stream()
                .mapToInt(TicketCategoriesPerEventResponseDto::totalQuantity)
                .sum();
        if (categoryQuantity <= 0) {
            throw new IllegalArgumentException("quantity must greater than 0");
        }

        if (totalCategoriesQuantity + categoryQuantity > capacity) {
            throw new InvalidValueException("The total quantity of categories exceeds event capacity");
        }

    }
}
