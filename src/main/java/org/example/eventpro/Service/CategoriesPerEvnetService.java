package org.example.eventpro.Service;

import org.example.eventpro.Domain.Event;
import org.example.eventpro.Domain.TicketCategoriesPerEvent;
import org.example.eventpro.Domain.TicketCategory;
import org.example.eventpro.Exception.ResourceNotFoundException;
import org.example.eventpro.Repository.EventCategoryRepository;
import org.example.eventpro.Repository.EventRepository;
import org.example.eventpro.Repository.TicketCategoryRepository;
import org.example.eventpro.dto.TicketCategoriesPerEventRequestDto;
import org.example.eventpro.dto.TicketCategoriesPerEventResponseDto;
import org.example.eventpro.mapper.TicketCategoriesPerEventMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriesPerEvnetService {

    private final EventCategoryRepository eventCategoryRepository ;
    private final EventRepository eventRepository ;
    private final TicketCategoriesPerEventMapper categoriesPerEventMapper ;
    private final TicketCategoryRepository ticketCategoryRepository;

    public CategoriesPerEvnetService(
            EventCategoryRepository eventCategoryRepository ,
            TicketCategoriesPerEventMapper categoriesPerEventMapper,
            EventRepository eventRepository,
            TicketCategoryRepository ticketCategoryRepository)
    {
        this.eventCategoryRepository = eventCategoryRepository ;
        this.categoriesPerEventMapper = categoriesPerEventMapper ;
        this.eventRepository = eventRepository ;
        this.ticketCategoryRepository =  ticketCategoryRepository;
    }

    public TicketCategoriesPerEventResponseDto createEventCategory(TicketCategoriesPerEventRequestDto request){

        Event event = eventRepository.findById(request.eventId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not Fount "));

        TicketCategory category = ticketCategoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        event.setCapacity(discountEventCapacity(event , request.totalQuantity()));

        // CategoriesPerEvnet to Entity
        TicketCategoriesPerEvent categoriesPerEvent = categoriesPerEventMapper.toEntity(request);

        categoriesPerEvent.setCategory(category);
        categoriesPerEvent.setEvent(event);

        return categoriesPerEventMapper.toResponse(eventCategoryRepository.save(categoriesPerEvent));

    }

    public int discountEventCapacity(Event event , int categoryQuantity){
        int capacity = event.getCapacity();

        if(categoryQuantity <= 0){
            throw new IllegalArgumentException("quantity must greater than 0");
        }
        if(capacity < categoryQuantity){
            throw new IllegalArgumentException("quantity is out of capacity");
        }
        return capacity - categoryQuantity ;
    }
}
