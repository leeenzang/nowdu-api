package com.tauceti.nowdu.routine.controller;

import com.tauceti.nowdu.routine.dto.RoutineRequest;
import com.tauceti.nowdu.routine.dto.RoutineResponse;
import com.tauceti.nowdu.routine.service.RoutineService;
import com.tauceti.nowdu.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Routine", description = "루틴 API")
@RestController
@RequestMapping("/routines")
@RequiredArgsConstructor
public class RoutineController {

    private final RoutineService routineService;

    @Operation(summary = "루틴 목록 조회")
    @GetMapping
    public ResponseEntity<List<RoutineResponse>> getRoutines(
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(routineService.getRoutines(user));
    }

    @Operation(summary = "루틴 생성")
    @PostMapping
    public ResponseEntity<RoutineResponse> createRoutine(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody RoutineRequest request) {
        return ResponseEntity.ok(routineService.createRoutine(user, request));
    }

    @Operation(summary = "루틴 수정 (앞으로 모두)")
    @PutMapping("/{routineId}")
    public ResponseEntity<RoutineResponse> updateRoutine(
            @AuthenticationPrincipal User user,
            @PathVariable Long routineId,
            @Valid @RequestBody RoutineRequest request) {
        return ResponseEntity.ok(routineService.updateRoutine(user, routineId, request));
    }

    @Operation(summary = "루틴 삭제 (앞으로 모두)")
    @DeleteMapping("/{routineId}")
    public ResponseEntity<Void> deleteRoutine(
            @AuthenticationPrincipal User user,
            @PathVariable Long routineId) {
        routineService.deleteRoutine(user, routineId);
        return ResponseEntity.noContent().build();
    }
}