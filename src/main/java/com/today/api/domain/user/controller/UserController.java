package com.today.api.domain.user.controller;

import com.today.api.domain.user.dto.UpdateProfileRequest;
import com.today.api.domain.user.dto.UserProfileResponse;
import com.today.api.domain.user.dto.UserResponse;
import com.today.api.domain.user.model.User;
import com.today.api.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getMyProfile(
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = Long.parseLong(userDetails.getUsername());
        User user = userService.getUserProfile(userId);
        return ResponseEntity.ok(new UserProfileResponse(
                user.getId(),
                user.getNickname(),
                user.isNotificationSetting(),
                user.isSubscriber(),
                user.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileResponse> getUserProfile(@PathVariable Long userId) {
        User user = userService.getUserProfile(userId);
        return ResponseEntity.ok(new UserProfileResponse(
                user.getId(),
                user.getNickname(),
                user.isNotificationSetting(),
                user.isSubscriber(),
                user.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateMyProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UpdateProfileRequest request) {
        Long userId = Long.parseLong(userDetails.getUsername());

        User user = userService.getUserProfile(userId);

        if (request.getNickname() != null && !request.getNickname().isBlank()) {
            user = userService.updateNickname(userId, request.getNickname());
        }

        if (request.getNotificationSetting() != null) {
            user = userService.updateNotificationSetting(userId, request.getNotificationSetting());
        }

        return ResponseEntity.ok(new UserResponse(
                user.getId(),
                user.getNickname(),
                user.isNotificationSetting(),
                user.isSubscriber()));
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deactivateMyAccount(
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = Long.parseLong(userDetails.getUsername());
        userService.deactivateUser(userId);
        return ResponseEntity.ok().build();
    }
}
