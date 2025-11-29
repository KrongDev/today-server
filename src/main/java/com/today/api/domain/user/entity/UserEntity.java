package com.today.api.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "notification_setting", nullable = false)
    private boolean notificationSetting = true;

    @Column(name = "is_subscriber", nullable = false)
    private boolean isSubscriber = false;

    @Column(name = "is_deactivated", nullable = false)
    private boolean isDeactivated = false;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public UserEntity(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }

    public UserEntity(com.today.api.domain.user.model.User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.notificationSetting = user.isNotificationSetting();
        this.isSubscriber = user.isSubscriber();
        this.isDeactivated = user.isDeactivated();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
        this.deletedAt = user.getDeletedAt();
    }

    public com.today.api.domain.user.model.User toDomain() {
        return new com.today.api.domain.user.model.User(
                this.id,
                this.nickname,
                this.email,
                this.notificationSetting,
                this.isSubscriber,
                this.isDeactivated,
                this.createdAt,
                this.updatedAt,
                this.deletedAt);
    }

    // Business methods
    public void updateNickname(String nickname) {
        if (nickname != null && !nickname.isBlank()) {
            this.nickname = nickname;
        }
    }

    public void updateNotificationSetting(boolean notificationSetting) {
        this.notificationSetting = notificationSetting;
    }

    public void deactivate() {
        this.isDeactivated = true;
        this.deletedAt = LocalDateTime.now();
    }

    public void subscribe() {
        this.isSubscriber = true;
    }

    public void unsubscribe() {
        this.isSubscriber = false;
    }
}
