package com.today.api.domain.schedule.controller;

import com.today.api.domain.schedule.dto.*;
import com.today.api.domain.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponse> createSchedule(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody CreateScheduleRequest request) {
        Long userId = Long.parseLong(userDetails.getUsername());
        return ResponseEntity.ok(scheduleService.createSchedule(userId, request));
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponse> getSchedule(@PathVariable Long scheduleId) {
        return ResponseEntity.ok(scheduleService.getSchedule(scheduleId));
    }

    @GetMapping("/my")
    public ResponseEntity<List<ScheduleResponse>> getMySchedules(
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = Long.parseLong(userDetails.getUsername());
        return ResponseEntity.ok(scheduleService.getUserSchedules(userId));
    }

    @GetMapping("/my/range")
    public ResponseEntity<List<ScheduleResponse>> getMySchedulesByDateRange(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        Long userId = Long.parseLong(userDetails.getUsername());
        return ResponseEntity.ok(scheduleService.getUserSchedulesByDateRange(userId, startDate, endDate));
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponse> updateSchedule(
            @PathVariable Long scheduleId,
            @RequestBody UpdateScheduleRequest request) {
        return ResponseEntity.ok(scheduleService.updateSchedule(scheduleId, request));
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sync")
    public ResponseEntity<SyncResponse> syncSchedules(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody SyncRequest request) {
        Long userId = Long.parseLong(userDetails.getUsername());
        return ResponseEntity.ok(scheduleService.syncSchedules(userId, request));
    }

    @GetMapping("/availability")
    public ResponseEntity<AvailabilityResponse> checkAvailability(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(scheduleService.checkAvailability(userId, startDate, endDate));
    }
}
