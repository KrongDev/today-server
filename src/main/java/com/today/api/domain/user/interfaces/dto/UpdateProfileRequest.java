package com.today.api.domain.user.interfaces.dto;

public record UpdateProfileRequest(
        String nickname,
        Boolean notificationSetting) {
}
