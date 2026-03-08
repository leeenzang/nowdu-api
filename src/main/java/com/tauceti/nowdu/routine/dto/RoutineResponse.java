package com.tauceti.nowdu.routine.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tauceti.nowdu.routine.domain.Routine;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@Builder
public class RoutineResponse {

    private Long id;
    private String title;
    private String memo;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;

    private boolean mon;
    private boolean tue;
    private boolean wed;
    private boolean thu;
    private boolean fri;
    private boolean sat;
    private boolean sun;

    public static RoutineResponse from(Routine routine) {
        return RoutineResponse.builder()
                .id(routine.getId())
                .title(routine.getTitle())
                .memo(routine.getMemo())
                .startTime(routine.getStartTime())
                .endTime(routine.getEndTime())
                .mon(routine.isMon())
                .tue(routine.isTue())
                .wed(routine.isWed())
                .thu(routine.isThu())
                .fri(routine.isFri())
                .sat(routine.isSat())
                .sun(routine.isSun())
                .build();
    }
}