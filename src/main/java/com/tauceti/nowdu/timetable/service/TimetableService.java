package com.tauceti.nowdu.timetable.service;

import com.tauceti.nowdu.event.domain.Event;
import com.tauceti.nowdu.event.repository.EventRepository;
import com.tauceti.nowdu.routine.domain.Routine;
import com.tauceti.nowdu.routine.repository.RoutineRepository;
import com.tauceti.nowdu.timetable.dto.TimetableResponse;
import com.tauceti.nowdu.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TimetableService {

    private final RoutineRepository routineRepository;
    private final EventRepository eventRepository;

    // 일간 조회
    public List<TimetableResponse> getDailyTimetable(User user, LocalDate date) {
        List<TimetableResponse> result = new ArrayList<>();

        // 해당 날짜 요일에 맞는 루틴 필터링
        List<Routine> routines = routineRepository.findAllByUserId(user.getId())
                .stream()
                .filter(r -> isRoutineOnDate(r, date))
                .toList();

        // 1회성 일정 조회
        List<Event> events = eventRepository.findAllByUserIdAndEventDate(user.getId(), date);

        // 합쳐서 시간순 정렬
        routines.forEach(r -> result.add(TimetableResponse.fromRoutine(r)));
        events.forEach(e -> result.add(TimetableResponse.fromEvent(e)));
        result.sort(Comparator.comparing(TimetableResponse::getStartTime));

        return result;
    }

    // 주간 조회
    public Map<LocalDate, List<TimetableResponse>> getWeeklyTimetable(User user, LocalDate startDate) {
        return startDate.datesUntil(startDate.plusDays(7))
                .collect(Collectors.toMap(
                        date -> date,
                        date -> getDailyTimetable(user, date)
                ));
    }

    // 해당 날짜 요일에 루틴이 있는지 확인
    private boolean isRoutineOnDate(Routine routine, LocalDate date) {
        DayOfWeek dow = date.getDayOfWeek();
        return switch (dow) {
            case MONDAY -> routine.isMon();
            case TUESDAY -> routine.isTue();
            case WEDNESDAY -> routine.isWed();
            case THURSDAY -> routine.isThu();
            case FRIDAY -> routine.isFri();
            case SATURDAY -> routine.isSat();
            case SUNDAY -> routine.isSun();
        };
    }
}