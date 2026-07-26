package com.quickticket.api.domain.event.dto;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class EventCreateRequest {
    private String name;
    private LocalDateTime openTime;
    private Integer couponQuota;
    private Integer maxRow;
    private Integer maxCol;
}