package com.tauceti.nowdu.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tauceti.nowdu.event.domain.Event;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class EventResponse {

    private Long id;
    private String title;
    private String memo;
    private LocalDate eventDate;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;
    public static EventResponse from(Event event) {
        return EventResponse.builder()
                .id(event.getId())
                .title(event.getTitle())
                .memo(event.getMemo())
                .eventDate(event.getEventDate())
                .startTime(event.getStartTime())
                .build();
    }
}