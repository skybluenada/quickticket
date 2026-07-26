package com.quickticket.api.domain.reservation.repository;

import com.quickticket.api.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    
    // 세션 키(UUID)를 기반으로 특정 예매 내역을 찾는 메서드
    Optional<Reservation> findBySessionKey(String sessionKey);
}