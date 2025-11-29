package com.today.api.domain.user.interfaces.dto;

import com.today.api.domain.user.domain.model.User;

import java.time.format.DateTimeFormatter;

public record UserProfileResponse(
        Long id,
        String nickname,
        boolean notificationSetting,
        boolean isSubscriber,
        String createdAt) {
    public static UserProfileResponse from(User user) {
        return new UserProfileResponse(
                user.getId(),
                user.getNickname(),
                user.isNotificationSetting(),
                user.isSubscriber(),
                user.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }
}
