package com.today.api.domain.schedule.service;

import com.today.api.domain.schedule.dto.*;
import com.today.api.domain.schedule.entity.ScheduleEntity;
import com.today.api.domain.schedule.entity.ScheduleParticipantEntity;
import com.today.api.domain.schedule.model.Schedule;
import com.today.api.domain.schedule.repository.ScheduleDomainRepository;
import com.today.api.domain.schedule.repository.ScheduleParticipantRepository;
import com.today.api.domain.user.entity.UserEntity;
import com.today.api.domain.user.repository.UserJpaRepository;
import com.today.api.global.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

        private final ScheduleDomainRepository scheduleDomainRepository;
        private final ScheduleParticipantRepository participantRepository;
        private final UserJpaRepository userRepository;

        @Transactional
        public ScheduleResponse createSchedule(Long userId, CreateScheduleRequest request) {
                UserEntity user = userRepository.findById(userId)
                                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

                // Create domain model
                Schedule schedule = Schedule.create(
                                request.getTitle(),
                                request.getDetails(),
                                request.getLocation(),
                                request.getStartTime(),
                                request.getEndTime());

                // Save via domain repository
                schedule = scheduleDomainRepository.save(schedule);

                // Add creator as participant (infrastructure concern)
                ScheduleEntity scheduleEntity = new ScheduleEntity(schedule);
                ScheduleParticipantEntity participant = new ScheduleParticipantEntity(scheduleEntity, user);
                participantRepository.save(participant);

                return toScheduleResponse(schedule);
        }

        public ScheduleResponse getSchedule(Long scheduleId) {
                Schedule schedule = scheduleDomainRepository.findById(scheduleId)
                                .orElseThrow(() -> new ResourceNotFoundException("Schedule", "id", scheduleId));
                return toScheduleResponse(schedule);
        }

        public List<ScheduleResponse> getUserSchedules(Long userId) {
                return scheduleDomainRepository.findAllUserSchedules(userId).stream()
                                .map(this::toScheduleResponse)
                                .collect(Collectors.toList());
        }

        public List<ScheduleResponse> getUserSchedulesByDateRange(
                        Long userId, LocalDateTime startDate, LocalDateTime endDate) {
                return scheduleDomainRepository.findUserSchedulesByDateRange(userId, startDate, endDate).stream()
                                .map(this::toScheduleResponse)
                                .collect(Collectors.toList());
        }

        @Transactional
        public ScheduleResponse updateSchedule(Long scheduleId, UpdateScheduleRequest request) {
                // Load domain model
                Schedule schedule = scheduleDomainRepository.findById(scheduleId)
                                .orElseThrow(() -> new ResourceNotFoundException("Schedule", "id", scheduleId));

                // Delegate to domain model
                schedule.updateDetails(
                                request.getTitle(),
                                request.getDetails(),
                                request.getLocation(),
                                request.getStartTime(),
                                request.getEndTime());

                // Save
                schedule = scheduleDomainRepository.save(schedule);

                return toScheduleResponse(schedule);
        }

        @Transactional
        public void deleteSchedule(Long scheduleId) {
                Schedule schedule = scheduleDomainRepository.findById(scheduleId)
                                .orElseThrow(() -> new ResourceNotFoundException("Schedule", "id", scheduleId));
                scheduleDomainRepository.delete(schedule);
        }

        public AvailabilityResponse checkAvailability(Long userId, LocalDateTime startDate,
                        LocalDateTime endDate) {
                List<Schedule> schedules = scheduleDomainRepository.findUserSchedulesByDateRange(userId, startDate,
                                endDate);
                List<TimeSlot> busySlots = schedules.stream()
                                .map(s -> new TimeSlot(
                                                s.getStartTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                                                s.getEndTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                                .collect(Collectors.toList());

                return new AvailabilityResponse(userId, busySlots);
        }

        @Transactional
        public SyncResponse syncSchedules(Long userId, SyncRequest request) {
                UserEntity user = userRepository.findById(userId)
                                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

                if (request.getChanges() != null) {
                        for (ScheduleChange change : request.getChanges()) {
                                if ("CREATE".equalsIgnoreCase(change.getAction())) {
                                        Schedule schedule = Schedule.create(
                                                        change.getTitle(),
                                                        "", // details
                                                        "", // location
                                                        LocalDateTime.parse(change.getStartTime(),
                                                                        DateTimeFormatter.ISO_DATE_TIME),
                                                        LocalDateTime.parse(change.getEndTime(),
                                                                        DateTimeFormatter.ISO_DATE_TIME));
                                        schedule = scheduleDomainRepository.save(schedule);

                                        ScheduleEntity scheduleEntity = new ScheduleEntity(schedule);
                                        ScheduleParticipantEntity participant = new ScheduleParticipantEntity(
                                                        scheduleEntity, user);
                                        participantRepository.save(participant);
                                }
                                // Handle UPDATE and DELETE similarly
                        }
                }

                return new SyncResponse(
                                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
                                List.of() // No updates from server for now
                );
        }

        private ScheduleResponse toScheduleResponse(Schedule schedule) {
                DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                return new ScheduleResponse(
                                schedule.getId(),
                                schedule.getTitle(),
                                schedule.getDetails(),
                                schedule.getLocation(),
                                schedule.getStartTime().format(formatter),
                                schedule.getEndTime().format(formatter),
                                schedule.getCreatedAt().format(formatter),
                                schedule.getUpdatedAt().format(formatter));
        }
}
