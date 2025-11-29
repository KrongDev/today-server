package com.today.api.domain.schedule.interfaces.controller;

import com.today.api.domain.schedule.application.service.ScheduleFacade;
import com.today.api.domain.schedule.interfaces.dto.CreateScheduleRequest;
import com.today.api.domain.schedule.interfaces.dto.ScheduleResponse;
import com.today.api.domain.schedule.interfaces.dto.UpdateScheduleRequest;
import com.today.api.global.security.oauth2.user.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleFacade scheduleFacade;

    @PostMapping
    public ResponseEntity<ScheduleResponse> createSchedule(
            @AuthenticationPrincipal UserPrincipal userDetails,
            @RequestBody CreateScheduleRequest request) {
        return ResponseEntity.ok(scheduleFacade.createSchedule(userDetails.getId(), request));
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponse> getSchedule(@PathVariable Long scheduleId) {
        return ResponseEntity.ok(scheduleFacade.getSchedule(scheduleId));
    }

    @GetMapping("/my")
    public ResponseEntity<List<ScheduleResponse>> getMySchedules(
            @AuthenticationPrincipal UserPrincipal userDetails) {
        return ResponseEntity.ok(scheduleFacade.getUserSchedules(userDetails.getId()));
    }

    @GetMapping("/my/range")
    public ResponseEntity<List<ScheduleResponse>> getMySchedulesByDateRange(
            @AuthenticationPrincipal UserPrincipal userDetails,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(
                scheduleFacade.getUserSchedulesByDateRange(userDetails.getId(), startDate, endDate));
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponse> updateSchedule(
            @PathVariable Long scheduleId,
            @RequestBody UpdateScheduleRequest request) {
        return ResponseEntity.ok(scheduleFacade.updateSchedule(scheduleId, request));
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long scheduleId) {
        scheduleFacade.deleteSchedule(scheduleId);
        return ResponseEntity.ok().build();
    }
}
