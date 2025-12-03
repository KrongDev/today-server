package com.today.api.domain.schedule.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {
    private final Long id;
    private String title;
    private String details;
    private String location;
    private Long tagId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Factory method
    public static Schedule create(String title, String details, String location, Long tagId,
            LocalDateTime startTime, LocalDateTime endTime) {
        LocalDateTime now = LocalDateTime.now();
        return new Schedule(null, title, details, location, tagId, startTime, endTime, now, now);
    }

    // Business Logic
    public void updateDetails(String title, String details, String location, Long tagId,
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
        this.tagId = tagId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isOverlapping(LocalDateTime checkStart, LocalDateTime checkEnd) {
        return !this.endTime.isBefore(checkStart) && !this.startTime.isAfter(checkEnd);
    }
}
