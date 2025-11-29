package com.today.api.domain.schedule.infrastructure;

import com.today.api.domain.schedule.entity.ScheduleEntity;
import com.today.api.domain.schedule.model.Schedule;
import com.today.api.domain.schedule.repository.ScheduleDomainRepository;
import com.today.api.domain.schedule.repository.ScheduleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ScheduleRepositoryImpl implements ScheduleDomainRepository {

    private final ScheduleJpaRepository scheduleJpaRepository;

    @Override
    public Schedule save(Schedule schedule) {
        // Domain -> Entity
        ScheduleEntity entity = new ScheduleEntity(schedule);

        // Save
        ScheduleEntity savedEntity = scheduleJpaRepository.save(entity);

        // Entity -> Domain
        return savedEntity.toDomain();
    }

    @Override
    public Optional<Schedule> findById(Long id) {
        return scheduleJpaRepository.findById(id)
                .map(ScheduleEntity::toDomain);
    }

    @Override
    public List<Schedule> findAllUserSchedules(Long userId) {
        return scheduleJpaRepository.findAllUserSchedules(userId).stream()
                .map(ScheduleEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Schedule> findUserSchedulesByDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        return scheduleJpaRepository.findUserSchedulesByDateRange(userId, startDate, endDate).stream()
                .map(ScheduleEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Schedule schedule) {
        if (schedule.getId() != null) {
            scheduleJpaRepository.deleteById(schedule.getId());
        }
    }
}
