package org.example.eventpro.Service;

import org.example.eventpro.Domain.Event;
import org.example.eventpro.Domain.Organizer;
import org.example.eventpro.Enums.EventStatus;
import org.example.eventpro.Exception.ResourceNotFoundException;
import org.example.eventpro.Repository.EventRepository;
import org.example.eventpro.Repository.OrganizerRepository;
import org.example.eventpro.dto.CreateEventRequestDto;
import org.example.eventpro.dto.EventResponseDto;
import org.example.eventpro.mapper.EventMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class EventService {
    private final EventRepository eventRepository;
    private final OrganizerRepository organizerRepository;
    private final EventMapper eventMapper;

    public EventService(EventRepository eventRepository,
                        OrganizerRepository organizerRepository,
                        EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.organizerRepository = organizerRepository;
        this.eventMapper = eventMapper;
    }

    public EventResponseDto create(Long organizerId, CreateEventRequestDto request) {
        validateDates(request);
        Organizer organizer = getOrganizer(organizerId);

        Event event = eventMapper.toEntity(request);
        event.setOrganizer(organizer);
        event.setStatus(EventStatus.DRAFT);
        return eventMapper.toResponseDto(eventRepository.save(event));
    }

    @Transactional(readOnly = true)
    public List<EventResponseDto> findAll() {
        return eventRepository.findAll().stream().map(eventMapper::toResponseDto).toList();
    }

    @Transactional(readOnly = true)
    public List<EventResponseDto> findAllByOrganizer(Long organizerId) {
        getOrganizer(organizerId);
        return eventRepository.findAllByOrganizerId(organizerId).stream()
                .map(eventMapper::toResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public EventResponseDto findById(Long id) {
        return eventMapper.toResponseDto(getEvent(id));
    }

    public EventResponseDto update(Long id, CreateEventRequestDto request) {
        validateDates(request);
        Event event = getEvent(id);
        eventMapper.updateEvent(request, event);
        return eventMapper.toResponseDto(eventRepository.save(event));
    }

    public void delete(Long id) {
        eventRepository.delete(getEvent(id));
    }

    private Event getEvent(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
    }

    private Organizer getOrganizer(Long id) {
        return organizerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organizer not found"));
    }

    private void validateDates(CreateEventRequestDto request) {
        if (request.getEndDate() != null && request.getStartDate() != null
                && request.getEndDate().isBefore(request.getStartDate())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Event end date must be on or after its start date");
        }
        if (request.getSalesClosingDate() != null && request.getSalesOpeningDate() != null
                && request.getSalesClosingDate().isBefore(request.getSalesOpeningDate())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sales closing date must be on or after its opening date");
        }
    }
}
