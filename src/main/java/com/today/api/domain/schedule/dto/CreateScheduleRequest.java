package com.today.api.domain.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateScheduleRequest {
    private String title;
    private String details;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
