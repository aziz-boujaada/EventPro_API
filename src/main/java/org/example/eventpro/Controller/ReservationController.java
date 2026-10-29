package org.example.eventpro.Controller;

import jakarta.validation.Valid;
import org.example.eventpro.Service.ReservationService;
import org.example.eventpro.dto.Reservation.CreateReservationRequest;
import org.example.eventpro.dto.Reservation.ReservationResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService){
        this.reservationService = reservationService ;
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> create(@Valid @RequestBody CreateReservationRequest requestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.createReservation(requestDto));
    }

}
