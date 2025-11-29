package com.today.api.domain.schedule.repository;

import com.today.api.domain.schedule.model.Schedule;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleDomainRepository {
    Schedule save(Schedule schedule);

    Optional<Schedule> findById(Long id);

    List<Schedule> findAllUserSchedules(Long userId);

    List<Schedule> findUserSchedulesByDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate);

    void delete(Schedule schedule);
}
