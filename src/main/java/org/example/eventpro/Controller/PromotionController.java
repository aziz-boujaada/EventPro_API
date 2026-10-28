package org.example.eventpro.Controller;

import jakarta.validation.Valid;

import org.example.eventpro.Domain.Promotion;
import org.example.eventpro.Service.PromotionService;
import org.example.eventpro.dto.CreatePromotionRequest;
import org.example.eventpro.dto.PromotionResponseDto;
import org.example.eventpro.mapper.PromotionMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promotions")
public class PromotionController {

    private final PromotionService promotionService;
    private final PromotionMapper promotionMapper;
    public PromotionController(PromotionService promotionService , PromotionMapper promotionMapper)  {
        this.promotionService = promotionService;
        this.promotionMapper = promotionMapper;
    }

    @PostMapping
    public ResponseEntity<PromotionResponseDto> create(
            @Valid @RequestBody CreatePromotionRequest dto
    ) {
        PromotionResponseDto response = promotionService.createPromotion(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromotionResponseDto> getById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
               promotionMapper.toResponse(promotionService.getPromotion(id))
        );
    }

    @GetMapping
    public ResponseEntity<List<PromotionResponseDto>> getAll() {
        return ResponseEntity.ok(
                promotionService.getAllPromotions()
        );
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<PromotionResponseDto>> getByEventId(
            @PathVariable Long eventId
    ) {
        return ResponseEntity.ok(
                promotionService.getPromotionsByEventId(eventId)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<org.example.eventpro.dto.PromotionResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody CreatePromotionRequest request
    ) {
        return ResponseEntity.ok(
                promotionService.updatePromotion(request , id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        promotionService.delete(id);

        return ResponseEntity.noContent().build();
    }
}