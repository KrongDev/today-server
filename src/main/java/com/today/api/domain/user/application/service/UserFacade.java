package com.today.api.domain.user.application.service;

import com.today.api.domain.user.domain.model.User;
import com.today.api.domain.user.domain.service.UserService;
import com.today.api.domain.user.interfaces.dto.UpdateProfileRequest;
import com.today.api.domain.user.interfaces.dto.UserProfileResponse;
import com.today.api.domain.user.interfaces.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;

    @Transactional(readOnly = true)
    public UserProfileResponse getMyProfile(Long userId) {
        User user = userService.getUserProfile(userId);
        return UserProfileResponse.from(user);
    }

    @Transactional(readOnly = true)
    public UserProfileResponse getUserProfile(Long userId) {
        User user = userService.getUserProfile(userId);
        return UserProfileResponse.from(user);
    }

    @Transactional
    public UserResponse updateMyProfile(Long userId, UpdateProfileRequest request) {
        User user = userService.getUserProfile(userId);

        if (request.nickname() != null && !request.nickname().isBlank()) {
            user = userService.updateNickname(userId, request.nickname());
        }

        if (request.notificationSetting() != null) {
            user = userService.updateNotificationSetting(userId, request.notificationSetting());
        }

        return UserResponse.from(user);
    }

    @Transactional
    public void deactivateMyAccount(Long userId) {
        userService.deactivateUser(userId);
    }
}
