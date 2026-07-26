package com.quickticket.api.domain.reservation.dto;

import lombok.Getter;

@Getter
public class ReservationRequest {
    private Long eventId;
    private Long seatId;
}