package com.quickticket.api.domain.reservation.service;

import com.quickticket.api.domain.event.entity.Event;
import com.quickticket.api.domain.event.repository.EventRepository;
import com.quickticket.api.domain.reservation.dto.ReservationRequest;
import com.quickticket.api.domain.reservation.entity.Reservation;
import com.quickticket.api.domain.reservation.repository.ReservationRepository;
import com.quickticket.api.domain.seat.entity.Seat;
import com.quickticket.api.domain.seat.entity.SeatStatus;
import com.quickticket.api.domain.seat.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final EventRepository eventRepository;
    private final SeatRepository seatRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    public String reserveSeat(ReservationRequest request, String sessionKey) {
        // 1. 이벤트와 좌석 정보 조회
        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이벤트입니다."));
        
        Seat seat = seatRepository.findById(request.getSeatId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 좌석입니다."));

        // 2. 좌석 예매 가능 여부 확인
        if (seat.getStatus() != SeatStatus.AVAILABLE) {
            throw new IllegalStateException("이미 예매가 완료되었거나 진행 중인 좌석입니다.");
        }

        // 3. 좌석 상태를 RESERVED(예약됨)로 변경
        seat.setStatus(SeatStatus.RESERVED);

        // 4. 쿠폰 코드 8자리 생성 및 예매 내역 저장
        String couponCode = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        
        Reservation reservation = Reservation.builder()
                .event(event)
                .seat(seat)
                .sessionKey(sessionKey)
                .couponCode(couponCode)
                .createdAt(LocalDateTime.now())
                .build();
                
        reservationRepository.save(reservation);

        return couponCode;
    }
}