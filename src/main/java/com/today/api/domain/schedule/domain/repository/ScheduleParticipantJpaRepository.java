package com.today.api.domain.schedule.domain.repository;

import com.today.api.domain.schedule.infrastructure.entity.ScheduleParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleParticipantJpaRepository extends JpaRepository<ScheduleParticipantEntity, Long> {
}
