package com.today.api.domain.schedule.infrastructure.entity;

import com.today.api.domain.schedule.domain.model.Schedule;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter(AccessLevel.PACKAGE)
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
    public ScheduleEntity(Schedule schedule) {
        BeanUtils.copyProperties(schedule, this);
    }

    // Method: Entity -> Domain Model
    public Schedule toDomain() {
        return new Schedule(
                this.id,
                this.title,
                this.details,
                this.location,
                this.startTime,
                this.endTime,
                this.createdAt,
                this.updatedAt);
    }
}
