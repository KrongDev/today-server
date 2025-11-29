package com.today.api.domain.schedule.repository;

import com.today.api.domain.schedule.entity.ScheduleParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleParticipantRepository extends JpaRepository<ScheduleParticipantEntity, Long> {
    void deleteByScheduleIdAndUserId(Long scheduleId, Long userId);
}
