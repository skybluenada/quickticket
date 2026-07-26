package com.quickticket.api.domain.reservation.dto;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Builder
public class ReservationResponse {
    private String eventName;
    private String rowStr;
    private Integer colNum;
    private String couponCode;
    private LocalDateTime createdAt;
}