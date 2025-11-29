package com.today.api.domain.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SyncRequest {
    private String lastSyncTime;
    private List<ScheduleChange> changes;
}
