package com.quickticket.api.domain.seat.repository;

import com.quickticket.api.domain.seat.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    
    // 특정 이벤트 ID에 속한 모든 좌석을 조회하는 메서드
    List<Seat> findByEventId(Long eventId);

    void deleteByEventId(Long eventId);
}