package com.tauceti.nowdu.event.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class EventRequest {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    private String memo;

    @NotNull(message = "날짜를 입력해주세요.")
    private LocalDate eventDate;

    @NotNull(message = "시작 시간을 입력해주세요.")
    private LocalTime startTime;

    private LocalTime endTime; // optional
}