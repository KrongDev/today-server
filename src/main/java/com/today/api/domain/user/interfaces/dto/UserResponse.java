package com.today.api.domain.user.interfaces.dto;

import com.today.api.domain.user.domain.model.User;

import java.time.format.DateTimeFormatter;

public record UserResponse(
        Long id,
        String nickname,
        boolean notificationSetting,
        boolean isSubscriber) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getNickname(),
                user.isNotificationSetting(),
                user.isSubscriber());
    }
}
