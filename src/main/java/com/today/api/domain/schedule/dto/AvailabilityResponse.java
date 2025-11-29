package com.today.api.domain.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilityResponse {
    private Long userId;
    private List<TimeSlot> busySlots;
}
