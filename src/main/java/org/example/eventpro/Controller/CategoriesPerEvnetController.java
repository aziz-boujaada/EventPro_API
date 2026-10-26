package org.example.eventpro.Controller;

import jakarta.validation.Valid;
import org.example.eventpro.Service.CategoriesPerEvnetService;
import org.example.eventpro.dto.TicketCategoriesPerEventRequestDto;
import org.example.eventpro.dto.TicketCategoriesPerEventResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/evnt-categories")
public class CategoriesPerEvnetController {

    private final CategoriesPerEvnetService categoriesPerEvnetService;

    public CategoriesPerEvnetController(CategoriesPerEvnetService categoriesPerEvnetService) {
        this.categoriesPerEvnetService = categoriesPerEvnetService;
    }

    @PostMapping
    public ResponseEntity<TicketCategoriesPerEventResponseDto> createEventCategory(
            @Valid @RequestBody TicketCategoriesPerEventRequestDto requestDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriesPerEvnetService.createEventCategory(requestDto));
    }
}
