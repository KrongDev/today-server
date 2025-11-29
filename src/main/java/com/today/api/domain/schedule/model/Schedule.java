package com.today.api.domain.schedule.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Schedule {
    private final Long id;
    private String title;
    private String details;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Schedule(Long id, String title, String details, String location,
            LocalDateTime startTime, LocalDateTime endTime,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.details = details;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Factory method for creating new schedules
    public static Schedule create(String title, String details, String location,
            LocalDateTime startTime, LocalDateTime endTime) {
        LocalDateTime now = LocalDateTime.now();
        return new Schedule(null, title, details, location, startTime, endTime, now, now);
    }

    // Business Logic
    public void updateDetails(String title, String details, String location,
            LocalDateTime startTime, LocalDateTime endTime) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("Start time and end time are required");
        }
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }

        this.title = title;
        this.details = details;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isOverlapping(LocalDateTime checkStart, LocalDateTime checkEnd) {
        return !this.endTime.isBefore(checkStart) && !this.startTime.isAfter(checkEnd);
    }
}
