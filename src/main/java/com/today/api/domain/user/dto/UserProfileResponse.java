package com.today.api.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {
    private Long id;
    private String nickname;
    private boolean notificationSetting;
    private boolean isSubscriber;
    private String createdAt;
}
