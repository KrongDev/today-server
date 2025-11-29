package com.today.api.domain.user.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
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

    public User(Long id, String nickname, String email, boolean notificationSetting, boolean isSubscriber,
            boolean isDeactivated,
            LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.notificationSetting = notificationSetting;
        this.isSubscriber = isSubscriber;
        this.isDeactivated = isDeactivated;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    // Constructor for creating new users
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
