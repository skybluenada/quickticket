package com.quickticket.api.domain.event.dto;

import com.quickticket.api.domain.event.entity.EventStatus;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Builder
public class EventResponse {
    private Long id;
    private String name;
    private LocalDateTime openTime;
    private Integer couponQuota;
    private Integer maxRow;
    private Integer maxCol;
    private EventStatus status;
}