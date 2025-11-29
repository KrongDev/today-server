package com.today.api.domain.user.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class User {
    private final Long id;
    private String nickname;
    private String email;
    private boolean notificationSetting;
    private boolean isSubscriber;
    private boolean isDeactivated;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    // Factory method
    public static User create(String nickname, String email) {
        LocalDateTime now = LocalDateTime.now();
        return new User(null, nickname, email, true, false, false, now, now, null);
    }

    // Business Logic
    public void changeNickname(String newNickname) {
        if (newNickname == null || newNickname.isBlank()) {
            throw new IllegalArgumentException("Nickname cannot be empty");
        }
        this.nickname = newNickname;
        this.updatedAt = LocalDateTime.now();
    }

    public void changeNotificationSetting(boolean enabled) {
        this.notificationSetting = enabled;
        this.updatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        this.isDeactivated = true;
        this.deletedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void subscribe() {
        this.isSubscriber = true;
        this.updatedAt = LocalDateTime.now();
    }

    public void unsubscribe() {
        this.isSubscriber = false;
        this.updatedAt = LocalDateTime.now();
    }
}
