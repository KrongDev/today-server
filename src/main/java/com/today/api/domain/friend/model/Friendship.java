package com.today.api.domain.friend.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Friendship {
    private final Long id;
    private final Long requesterId;
    private final Long receiverId;
    private FriendshipStatus status;
    private final LocalDateTime createdAt;

    public Friendship(Long id, Long requesterId, Long receiverId, FriendshipStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.requesterId = requesterId;
        this.receiverId = receiverId;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Factory method for creating new friendship requests
    public static Friendship createRequest(Long requesterId, Long receiverId) {
        return new Friendship(null, requesterId, receiverId, FriendshipStatus.PENDING, LocalDateTime.now());
    }

    // Business Logic
    public void accept() {
        if (this.status != FriendshipStatus.PENDING) {
            throw new IllegalStateException("Only pending requests can be accepted");
        }
        this.status = FriendshipStatus.ACCEPTED;
    }

    public void block() {
        this.status = FriendshipStatus.BLOCKED;
    }

    public boolean isPending() {
        return this.status == FriendshipStatus.PENDING;
    }

    public boolean isAccepted() {
        return this.status == FriendshipStatus.ACCEPTED;
    }

    public boolean isBlocked() {
        return this.status == FriendshipStatus.BLOCKED;
    }

    public boolean involves(Long userId) {
        return this.requesterId.equals(userId) || this.receiverId.equals(userId);
    }

    public Long getOtherUserId(Long currentUserId) {
        if (this.requesterId.equals(currentUserId)) {
            return this.receiverId;
        } else if (this.receiverId.equals(currentUserId)) {
            return this.requesterId;
        }
        throw new IllegalArgumentException("User is not part of this friendship");
    }

    public enum FriendshipStatus {
        PENDING, ACCEPTED, BLOCKED
    }
}
