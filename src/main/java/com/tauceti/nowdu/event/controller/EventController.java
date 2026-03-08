package com.tauceti.nowdu.event.controller;

import com.tauceti.nowdu.event.dto.EventRequest;
import com.tauceti.nowdu.event.dto.EventResponse;
import com.tauceti.nowdu.event.service.EventService;
import com.tauceti.nowdu.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Event", description = "1회성 일정 API")
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @Operation(summary = "특정 날짜 일정 목록 조회")
    @GetMapping
    public ResponseEntity<List<EventResponse>> getEvents(
            @AuthenticationPrincipal User user,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(eventService.getEvents(user, date));
    }

    @Operation(summary = "일정 생성")
    @PostMapping
    public ResponseEntity<EventResponse> createEvent(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody EventRequest request) {
        return ResponseEntity.ok(eventService.createEvent(user, request));
    }

    @Operation(summary = "일정 수정")
    @PutMapping("/{eventId}")
    public ResponseEntity<EventResponse> updateEvent(
            @AuthenticationPrincipal User user,
            @PathVariable Long eventId,
            @Valid @RequestBody EventRequest request) {
        return ResponseEntity.ok(eventService.updateEvent(user, eventId, request));
    }

    @Operation(summary = "일정 삭제")
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(
            @AuthenticationPrincipal User user,
            @PathVariable Long eventId) {
        eventService.deleteEvent(user, eventId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "일정 완료 토글")
    @PatchMapping("/{eventId}/done")
    public ResponseEntity<EventResponse> toggleDone(
            @AuthenticationPrincipal User user,
            @PathVariable Long eventId) {
        return ResponseEntity.ok(eventService.toggleDone(user, eventId));
    }
}