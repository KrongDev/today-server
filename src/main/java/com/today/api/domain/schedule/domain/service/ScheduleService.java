package com.today.api.domain.schedule.domain.service;

import com.today.api.domain.schedule.domain.model.Schedule;
import com.today.api.domain.schedule.domain.repository.ScheduleDomainRepository;
import com.today.api.domain.schedule.domain.repository.ScheduleParticipantJpaRepository;
import com.today.api.domain.schedule.infrastructure.entity.ScheduleEntity;
import com.today.api.domain.schedule.infrastructure.entity.ScheduleParticipantEntity;
import com.today.api.global.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleDomainRepository scheduleDomainRepository;
    private final ScheduleParticipantJpaRepository participantRepository;

    @Transactional
    public Schedule createSchedule(Long userId, String title, String details, String location, Long tagId,
            LocalDateTime startTime, LocalDateTime endTime) {
        // Create domain model
        Schedule schedule = Schedule.create(title, details, location, tagId, startTime, endTime);

        // Save
        schedule = scheduleDomainRepository.save(schedule);

        // Add creator as participant
        ScheduleEntity scheduleEntity = new ScheduleEntity(schedule);
        ScheduleParticipantEntity participant = new ScheduleParticipantEntity(scheduleEntity, userId);
        participantRepository.save(participant);

        return schedule;
    }

    public Schedule getSchedule(Long scheduleId) {
        return scheduleDomainRepository.findById(scheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule", "id", scheduleId));
    }

    public List<Schedule> getUserSchedules(Long userId) {
        return scheduleDomainRepository.findAllUserSchedules(userId);
    }

    public List<Schedule> getUserSchedulesByDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        return scheduleDomainRepository.findUserSchedulesByDateRange(userId, startDate, endDate);
    }

    @Transactional
    public Schedule updateSchedule(Long scheduleId, String title, String details, String location, Long tagId,
            LocalDateTime startTime, LocalDateTime endTime) {
        Schedule schedule = getSchedule(scheduleId);
        schedule.updateDetails(title, details, location, tagId, startTime, endTime);
        return scheduleDomainRepository.save(schedule);
    }

    @Transactional
    public void deleteSchedule(Long scheduleId) {
        Schedule schedule = getSchedule(scheduleId);
        scheduleDomainRepository.delete(schedule);
    }
}
