package com.quickticket.api.domain.reservation.controller;

import com.quickticket.api.domain.reservation.dto.ReservationRequest;
import com.quickticket.api.domain.reservation.dto.ReservationResponse;
import com.quickticket.api.domain.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<String> reserve(
            @RequestHeader("Session-Key") String sessionKey,
            @RequestBody ReservationRequest request) {

        String couponCode = reservationService.reserveSeat(request, sessionKey);
        return ResponseEntity.ok("예매 성공! 발급된 쿠폰 코드: " + couponCode);
    }

    // [신규 추가됨] 내 예매 내역 조회 API
    @GetMapping
    public ResponseEntity<ReservationResponse> getMyReservation(
            @RequestHeader("Session-Key") String sessionKey) {

        ReservationResponse response = reservationService.getReservation(sessionKey);
        return ResponseEntity.ok(response);
    }
}