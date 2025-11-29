package com.today.api.domain.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponse {
    private Long id;
    private String title;
    private String details;
    private String location;
    private String startTime;
    private String endTime;
    private String createdAt;
    private String updatedAt;
}
