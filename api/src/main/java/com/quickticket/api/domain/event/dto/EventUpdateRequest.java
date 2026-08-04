package com.quickticket.api.domain.event.dto;

import com.quickticket.api.domain.event.entity.EventStatus;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class EventUpdateRequest {
    private String name;
    private LocalDateTime openTime;
    private Integer couponQuota;
    private EventStatus status;
}
