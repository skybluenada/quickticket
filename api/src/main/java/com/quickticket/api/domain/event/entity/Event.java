package com.quickticket.api.domain.event.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "event")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDateTime openTime;
    private Integer couponQuota;
    private Integer maxRow;
    private Integer maxCol;

    @Enumerated(EnumType.STRING)
    private EventStatus status;

}

public void update(String name, LocalDateTime openTime, Integer couponQuota, EventStatus status) {
    this.name = name;
    this.openTime = openTime;
    this.couponQuota = couponQuota;
    this.status = status;
}

// 긴급 차단용 메서드
public void emergencyStop() {
    this.status = EventStatus.PAUSED;
}
