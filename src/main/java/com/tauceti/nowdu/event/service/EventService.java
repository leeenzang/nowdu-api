package com.tauceti.nowdu.event.service;

import com.tauceti.nowdu.event.domain.Event;
import com.tauceti.nowdu.event.dto.EventRequest;
import com.tauceti.nowdu.event.dto.EventResponse;
import com.tauceti.nowdu.event.repository.EventRepository;
import com.tauceti.nowdu.global.exception.BusinessException;
import com.tauceti.nowdu.global.exception.ErrorCode;
import com.tauceti.nowdu.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventService {

    private final EventRepository eventRepository;

    // 특정 날짜 일정 목록 조회
    public List<EventResponse> getEvents(User user, LocalDate date) {
        return eventRepository.findAllByUserIdAndEventDate(user.getId(), date)
                .stream()
                .map(EventResponse::from)
                .toList();
    }

    // 일정 생성
    @Transactional
    public EventResponse createEvent(User user, EventRequest request) {
        Event event = Event.builder()
                .user(user)
                .title(request.getTitle())
                .memo(request.getMemo())
                .eventDate(request.getEventDate())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .build();

        return EventResponse.from(eventRepository.save(event));
    }

    // 일정 수정
    @Transactional
    public EventResponse updateEvent(User user, Long eventId, EventRequest request) {
        Event event = eventRepository.findByIdAndUserId(eventId, user.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.EVENT_NOT_FOUND));

        event.update(request.getTitle(), request.getMemo(),
                request.getEventDate(), request.getStartTime(), request.getEndTime());

        return EventResponse.from(event);
    }

    // 일정 삭제
    @Transactional
    public void deleteEvent(User user, Long eventId) {
        Event event = eventRepository.findByIdAndUserId(eventId, user.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.EVENT_NOT_FOUND));

        eventRepository.delete(event);
    }

    // 일정 완료 토글
    @Transactional
    public EventResponse toggleDone(User user, Long eventId) {
        Event event = eventRepository.findByIdAndUserId(eventId, user.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.EVENT_NOT_FOUND));

        event.toggleDone();

        return EventResponse.from(event);
    }
}