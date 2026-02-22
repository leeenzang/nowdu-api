package com.tauceti.nowdu.timetable.controller;

import com.tauceti.nowdu.timetable.dto.TimetableResponse;
import com.tauceti.nowdu.timetable.service.TimetableService;
import com.tauceti.nowdu.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Tag(name = "Timetable", description = "타임테이블 API")
@RestController
@RequestMapping("/timetable")
@RequiredArgsConstructor
public class TimetableController {

    private final TimetableService timetableService;

    @Operation(summary = "일간 타임테이블 조회")
    @GetMapping
    public ResponseEntity<List<TimetableResponse>> getDailyTimetable(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "#{T(java.time.LocalDate).now()}")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(timetableService.getDailyTimetable(user, date));
    }

    @Operation(summary = "주간 타임테이블 조회")
    @GetMapping("/week")
    public ResponseEntity<Map<LocalDate, List<TimetableResponse>>> getWeeklyTimetable(
            @AuthenticationPrincipal User user,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {
        return ResponseEntity.ok(timetableService.getWeeklyTimetable(user, startDate));
    }
}