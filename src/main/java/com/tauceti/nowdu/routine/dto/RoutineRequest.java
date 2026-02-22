package com.tauceti.nowdu.routine.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class RoutineRequest {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    private String memo;

    @NotNull(message = "시작 시간을 입력해주세요.")
    private LocalTime startTime;

    private boolean mon;
    private boolean tue;
    private boolean wed;
    private boolean thu;
    private boolean fri;
    private boolean sat;
    private boolean sun;
}