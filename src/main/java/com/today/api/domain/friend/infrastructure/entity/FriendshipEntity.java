package com.today.api.domain.friend.infrastructure.entity;

import com.today.api.domain.friend.domain.model.Friendship;
import com.today.api.domain.friend.domain.model.vo.FriendshipStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter(AccessLevel.PACKAGE)
@Table(name = "friendships")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class FriendshipEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long requesterId;
    private Long receiverId;
    @Enumerated(EnumType.STRING)
    private FriendshipStatus status;
    @CreatedDate
    private LocalDateTime createdAt;

    // Constructor: Domain Model -> Entity (requires UserEntity lookup)
    public FriendshipEntity(Friendship friendship) {
        BeanUtils.copyProperties(friendship, this);
    }

    // Method: Entity -> Domain Model
    public Friendship toDomain() {
        return new Friendship(
                this.id,
                this.requesterId,
                this.receiverId,
                FriendshipStatus.valueOf(this.status.name()),
                this.createdAt);
    }
}
