package com.today.api.domain.schedule.domain.repository;

import com.today.api.domain.schedule.infrastructure.entity.ScheduleEntity;
import com.today.api.domain.schedule.infrastructure.entity.ScheduleParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleJpaRepository extends JpaRepository<ScheduleEntity, Long> {

    @Query("SELECT s FROM ScheduleEntity s JOIN ScheduleParticipantEntity sp ON s.id = sp.schedule.id WHERE sp.userId = :userId")
    List<ScheduleEntity> findAllUserSchedules(@Param("userId") Long userId);

    @Query("SELECT s FROM ScheduleEntity s JOIN ScheduleParticipantEntity sp ON s.id = sp.schedule.id " +
            "WHERE sp.userId = :userId AND s.startTime >= :startDate AND s.endTime <= :endDate")
    List<ScheduleEntity> findUserSchedulesByDateRange(
            @Param("userId") Long userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}
