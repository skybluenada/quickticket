package com.quickticket.api.domain.event.service;

import com.quickticket.api.domain.event.dto.EventCreateRequest;
import com.quickticket.api.domain.event.entity.Event;
import com.quickticket.api.domain.event.entity.EventStatus;
import com.quickticket.api.domain.event.repository.EventRepository;
import com.quickticket.api.domain.seat.entity.Seat;
import com.quickticket.api.domain.seat.entity.SeatStatus;
import com.quickticket.api.domain.seat.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final SeatRepository seatRepository;

    @Transactional
    public Long createEvent(EventCreateRequest request) {
        // 1. 이벤트 생성 및 저장
        Event event = Event.builder()
                .name(request.getName())
                .openTime(request.getOpenTime())
                .couponQuota(request.getCouponQuota())
                .maxRow(request.getMaxRow())
                .maxCol(request.getMaxCol())
                .status(EventStatus.PENDING)
                .build();
        
        Event savedEvent = eventRepository.save(event);

        // 2. 설정된 크기(행x열)만큼 좌석 자동 생성
        List<Seat> seats = new ArrayList<>();
        char rowChar = 'A';
        
        for (int i = 0; i < request.getMaxRow(); i++) {
            String currentRow = String.valueOf((char) (rowChar + i));
            for (int j = 1; j <= request.getMaxCol(); j++) {
                Seat seat = Seat.builder()
                        .event(savedEvent)
                        .rowStr(currentRow)
                        .colNum(j)
                        .status(SeatStatus.AVAILABLE)
                        .build();
                seats.add(seat);
            }
        }
        
        seatRepository.saveAll(seats); // 만들어진 좌석 한 번에 저장

        return savedEvent.getId();
    }
}