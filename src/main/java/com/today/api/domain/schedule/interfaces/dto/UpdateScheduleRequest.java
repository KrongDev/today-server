package com.today.api.domain.schedule.interfaces.dto;

import java.time.LocalDateTime;

public record UpdateScheduleRequest(
        String title,
        String details,
        String location,
        LocalDateTime startTime,
        LocalDateTime endTime) {
}
