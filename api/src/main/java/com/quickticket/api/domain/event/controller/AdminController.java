package com.quickticket.api.domain.event.controller;

import com.quickticket.api.domain.event.dto.EventCreateRequest;
import com.quickticket.api.domain.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}