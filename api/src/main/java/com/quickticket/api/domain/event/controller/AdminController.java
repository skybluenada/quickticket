package com.quickticket.api.domain.event.controller;

import com.quickticket.api.domain.event.dto.EventCreateRequest;
import com.quickticket.api.domain.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final EventService eventService;

    @PostMapping("/events")
    public ResponseEntity<String> createEvent(@RequestBody EventCreateRequest request) {
        Long eventId = eventService.createEvent(request);
        return ResponseEntity.ok("이벤트가 성공적으로 생성되었습니다. Event ID: " + eventId);
    }
    // 1-1. 이벤트 목록 조회
    @GetMapping("/events")
    public ResponseEntity<List<EventResponse>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    // 1-2. 이벤트 단건 조회
    @GetMapping("/events/{eventId}")
    public ResponseEntity<EventResponse> getEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(eventService.getEvent(eventId));
    }

    // 1-3. 이벤트 수정
    @PutMapping("/events/{eventId}")
    public ResponseEntity<String> updateEvent(
            @PathVariable Long eventId,
            @RequestBody EventUpdateRequest request) {
        eventService.updateEvent(eventId, request);
        return ResponseEntity.ok("이벤트가 성공적으로 수정되었습니다. ID: " + eventId);
    }

    // 1-4. 이벤트 삭제
    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
        return ResponseEntity.ok("이벤트가 성공적으로 삭제되었습니다. ID: " + eventId);
    }

    // 1-5. 긴급 차단
    @PostMapping("/events/{eventId}/emergency-stop")
    public ResponseEntity<String> emergencyStop(@PathVariable Long eventId) {
        eventService.emergencyStop(eventId);
        return ResponseEntity.ok("긴급 차단이 발동되었습니다! 티켓팅이 일시 정지됩니다. ID: " + eventId);
    }
}