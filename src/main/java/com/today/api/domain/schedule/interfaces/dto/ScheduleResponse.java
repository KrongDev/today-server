package com.today.api.domain.schedule.interfaces.dto;

import com.today.api.domain.schedule.domain.model.Schedule;

import java.time.format.DateTimeFormatter;

public record ScheduleResponse(
        Long id,
        String title,
        String details,
        String location,
        Long tagId,
        String startTime,
        String endTime,
        String createdAt,
        String updatedAt) {
    public static ScheduleResponse from(Schedule schedule) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return new ScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getDetails(),
                schedule.getLocation(),
                schedule.getTagId(),
                schedule.getStartTime().format(formatter),
                schedule.getEndTime().format(formatter),
                schedule.getCreatedAt().format(formatter),
                schedule.getUpdatedAt().format(formatter));
    }
}
