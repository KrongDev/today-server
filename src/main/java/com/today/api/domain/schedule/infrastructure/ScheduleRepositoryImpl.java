package com.today.api.domain.schedule.infrastructure;

import com.today.api.domain.schedule.domain.model.Schedule;
import com.today.api.domain.schedule.domain.repository.ScheduleDomainRepository;
import com.today.api.domain.schedule.domain.repository.ScheduleJpaRepository;
import com.today.api.domain.schedule.infrastructure.entity.ScheduleEntity;
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
        ScheduleEntity entity = new ScheduleEntity(schedule);
        return scheduleJpaRepository.save(entity).toDomain();
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
