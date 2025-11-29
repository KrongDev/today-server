package com.today.api.domain.user.infrastructure.entity;

import com.today.api.domain.user.domain.model.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter(AccessLevel.PACKAGE)
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

    // Constructor: Domain Model -> Entity
    public UserEntity(User user) {
        BeanUtils.copyProperties(user, this);
    }

    // Method: Entity -> Domain Model
    public User toDomain() {
        return new User(
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
}
