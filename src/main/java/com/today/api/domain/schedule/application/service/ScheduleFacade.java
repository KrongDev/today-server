package com.today.api.domain.schedule.application.service;

import com.today.api.domain.schedule.domain.model.Schedule;
import com.today.api.domain.schedule.domain.service.ScheduleService;
import com.today.api.domain.schedule.interfaces.dto.CreateScheduleRequest;
import com.today.api.domain.schedule.interfaces.dto.ScheduleResponse;
import com.today.api.domain.schedule.interfaces.dto.UpdateScheduleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ScheduleFacade {

    private final ScheduleService scheduleService;

    @Transactional
    public ScheduleResponse createSchedule(Long userId, CreateScheduleRequest request) {
        Schedule schedule = scheduleService.createSchedule(
                userId,
                request.title(),
                request.details(),
                request.location(),
                request.tagId(),
                request.startTime(),
                request.endTime());
        return ScheduleResponse.from(schedule);
    }

    @Transactional(readOnly = true)
    public ScheduleResponse getSchedule(Long scheduleId) {
        Schedule schedule = scheduleService.getSchedule(scheduleId);
        return ScheduleResponse.from(schedule);
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponse> getUserSchedules(Long userId) {
        return scheduleService.getUserSchedules(userId).stream()
                .map(ScheduleResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponse> getUserSchedulesByDateRange(
            Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        return scheduleService.getUserSchedulesByDateRange(userId, startDate, endDate).stream()
                .map(ScheduleResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public ScheduleResponse updateSchedule(Long scheduleId, UpdateScheduleRequest request) {
        Schedule schedule = scheduleService.updateSchedule(
                scheduleId,
                request.title(),
                request.details(),
                request.location(),
                request.tagId(),
                request.startTime(),
                request.endTime());
        return ScheduleResponse.from(schedule);
    }

    @Transactional
    public void deleteSchedule(Long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
    }
}
