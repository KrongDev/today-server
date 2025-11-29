package com.today.api.domain.schedule.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "schedules")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class ScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String details;

    private String location;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public ScheduleEntity(String title, String details, String location, LocalDateTime startTime,
            LocalDateTime endTime) {
        this.title = title;
        this.details = details;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Constructor: Domain Model -> Entity
    public ScheduleEntity(com.today.api.domain.schedule.model.Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.details = schedule.getDetails();
        this.location = schedule.getLocation();
        this.startTime = schedule.getStartTime();
        this.endTime = schedule.getEndTime();
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getUpdatedAt();
    }

    // Method: Entity -> Domain Model
    public com.today.api.domain.schedule.model.Schedule toDomain() {
        return new com.today.api.domain.schedule.model.Schedule(
                this.id,
                this.title,
                this.details,
                this.location,
                this.startTime,
                this.endTime,
                this.createdAt,
                this.updatedAt);
    }

    // Business methods (kept for backward compatibility, but should delegate to
    // domain)
    public void updateSchedule(String title, String details, String location, LocalDateTime startTime,
            LocalDateTime endTime) {
        if (title != null && !title.isBlank()) {
            this.title = title;
        }
        this.details = details;
        this.location = location;
        if (startTime != null) {
            this.startTime = startTime;
        }
        if (endTime != null) {
            this.endTime = endTime;
        }
    }
}
