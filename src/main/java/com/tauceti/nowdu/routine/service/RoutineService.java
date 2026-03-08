package com.tauceti.nowdu.routine.service;

import com.tauceti.nowdu.global.exception.BusinessException;
import com.tauceti.nowdu.global.exception.ErrorCode;
import com.tauceti.nowdu.routine.domain.Routine;
import com.tauceti.nowdu.routine.dto.RoutineRequest;
import com.tauceti.nowdu.routine.dto.RoutineResponse;
import com.tauceti.nowdu.routine.repository.RoutineRepository;
import com.tauceti.nowdu.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoutineService {

    private final RoutineRepository routineRepository;

    // 루틴 목록 조회
    public List<RoutineResponse> getRoutines(User user) {
        return routineRepository.findAllByUserId(user.getId())
                .stream()
                .map(RoutineResponse::from)
                .toList();
    }

    // 루틴 생성
    @Transactional
    public RoutineResponse createRoutine(User user, RoutineRequest request) {
        Routine routine = Routine.builder()
                .user(user)
                .title(request.getTitle())
                .memo(request.getMemo())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .mon(request.isMon())
                .tue(request.isTue())
                .wed(request.isWed())
                .thu(request.isThu())
                .fri(request.isFri())
                .sat(request.isSat())
                .sun(request.isSun())
                .build();

        return RoutineResponse.from(routineRepository.save(routine));
    }

    // 루틴 수정 (앞으로 모두)
    @Transactional
    public RoutineResponse updateRoutine(User user, Long routineId, RoutineRequest request) {
        Routine routine = routineRepository.findByIdAndUserId(routineId, user.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.ROUTINE_NOT_FOUND));

        routine.update(
                request.getTitle(),
                request.getMemo(),
                request.getStartTime(),
                request.getEndTime(),
                request.isMon(), request.isTue(), request.isWed(),
                request.isThu(), request.isFri(), request.isSat(), request.isSun()
        );

        return RoutineResponse.from(routine);
    }

    // 루틴 삭제 (앞으로 모두)
    @Transactional
    public void deleteRoutine(User user, Long routineId) {
        Routine routine = routineRepository.findByIdAndUserId(routineId, user.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.ROUTINE_NOT_FOUND));

        routineRepository.delete(routine);
    }
}