package com.quickticket.api.domain.event.service;

import com.quickticket.api.domain.event.dto.EventCreateRequest;
import com.quickticket.api.domain.event.dto.EventUpdateRequest;
import com.quickticket.api.domain.event.entity.Event;
import com.quickticket.api.domain.event.entity.EventStatus;
import com.quickticket.api.domain.event.repository.EventRepository;
import com.quickticket.api.domain.seat.entity.Seat;
import com.quickticket.api.domain.seat.entity.SeatStatus;
import com.quickticket.api.domain.seat.repository.SeatRepository;
import com.quickticket.api.domain.event.dto.EventResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    // 1-1. 이벤트 목록 조회
    @Transactional(readOnly = true)
    public List<EventResponse> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(event -> EventResponse.builder()
                        .id(event.getId())
                        .name(event.getName())
                        .openTime(event.getOpenTime())
                        .couponQuota(event.getCouponQuota())
                        .maxRow(event.getMaxRow())
                        .maxCol(event.getMaxCol())
                        .status(event.getStatus())
                        .build())
                .collect(Collectors.toList());
    }

    // 1-2. 이벤트 단건 상세 조회
    @Transactional(readOnly = true)
    public EventResponse getEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이벤트입니다."));

        return EventResponse.builder()
                .id(event.getId())
                .name(event.getName())
                .openTime(event.getOpenTime())
                .couponQuota(event.getCouponQuota())
                .maxRow(event.getMaxRow())
                .maxCol(event.getMaxCol())
                .status(event.getStatus())
                .build();
    }

    // 1-3. 이벤트 정보 수정
    @Transactional
    public void updateEvent(Long eventId, EventUpdateRequest request) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이벤트입니다."));

        event.update(request.getName(), request.getOpenTime(), request.getCouponQuota(), request.getStatus());
    }

    // 1-4. 이벤트 삭제 (좌석 먼저 삭제 후 이벤트 삭제)
    @Transactional
    public void deleteEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이벤트입니다."));

        seatRepository.deleteByEventId(eventId);
        eventRepository.delete(event);
    }

    // 1-5. 긴급 차단(Emergency Stop) 스위치
    @Transactional
    public void emergencyStop(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이벤트입니다."));

        event.emergencyStop();
    }
}