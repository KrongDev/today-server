package com.today.api.domain.friend.entity;

import com.today.api.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "friendships", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "requester_id", "receiver_id" })
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class FriendshipEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id", nullable = false)
    private UserEntity requester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private UserEntity receiver;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FriendshipStatus status;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public FriendshipEntity(UserEntity requester, UserEntity receiver, FriendshipStatus status) {
        this.requester = requester;
        this.receiver = receiver;
        this.status = status;
    }

    // Constructor: Domain Model -> Entity (requires UserEntity lookup)
    public FriendshipEntity(com.today.api.domain.friend.model.Friendship friendship, UserEntity requester,
            UserEntity receiver) {
        this.id = friendship.getId();
        this.requester = requester;
        this.receiver = receiver;
        this.status = FriendshipStatus.valueOf(friendship.getStatus().name());
        this.createdAt = friendship.getCreatedAt();
    }

    // Method: Entity -> Domain Model
    public com.today.api.domain.friend.model.Friendship toDomain() {
        return new com.today.api.domain.friend.model.Friendship(
                this.id,
                this.requester.getId(),
                this.receiver.getId(),
                com.today.api.domain.friend.model.Friendship.FriendshipStatus.valueOf(this.status.name()),
                this.createdAt);
    }

    // Business methods (kept for backward compatibility)
    public void accept() {
        this.status = FriendshipStatus.ACCEPTED;
    }

    public enum FriendshipStatus {
        PENDING, ACCEPTED, BLOCKED
    }
}
