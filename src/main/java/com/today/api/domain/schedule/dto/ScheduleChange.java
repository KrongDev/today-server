package com.today.api.domain.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleChange {
    private String localId;
    private String title;
    private String startTime;
    private String endTime;
    private String action; // CREATE, UPDATE, DELETE
}
