package org.example.eventpro.Controller;

import jakarta.validation.Valid;
import org.example.eventpro.Service.EventService;
import org.example.eventpro.dto.CreateEventRequestDto;
import org.example.eventpro.dto.EventResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/organizers/{organizerId}")
    public ResponseEntity<EventResponseDto> createEvent(@PathVariable Long organizerId,
                                                        @Valid @RequestBody CreateEventRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.create(organizerId, request));
    }

    @GetMapping
    public List<EventResponseDto> getEvents() {
        return eventService.findAll();
    }

    @GetMapping("/organizers/{organizerId}")
    public List<EventResponseDto> getOrganizerEvents(@PathVariable Long organizerId) {
        return eventService.findAllByOrganizer(organizerId);
    }

    @GetMapping("/{id}")
    public EventResponseDto getEvent(@PathVariable Long id) {
        return eventService.findById(id);
    }

    @PutMapping("/{id}")
    public EventResponseDto updateEvent(@PathVariable Long id,
                                        @Valid @RequestBody CreateEventRequestDto request) {
        return eventService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
