package com.tauceti.nowdu.timetable.dto;

import com.tauceti.nowdu.event.domain.Event;
import com.tauceti.nowdu.routine.domain.Routine;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import com.fasterxml.jackson.annotation.JsonFormat;

@Getter
@Builder
public class TimetableResponse {

    private String type;       // "routine" or "event"
    private Long id;
    private String title;
    private String memo;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    public static TimetableResponse fromRoutine(Routine routine) {
        return TimetableResponse.builder()
                .type("routine")
                .id(routine.getId())
                .title(routine.getTitle())
                .memo(routine.getMemo())
                .startTime(routine.getStartTime())
                .build();
    }

    public static TimetableResponse fromEvent(Event event) {
        return TimetableResponse.builder()
                .type("event")
                .id(event.getId())
                .title(event.getTitle())
                .memo(event.getMemo())
                .startTime(event.getStartTime())
                .build();
    }
}