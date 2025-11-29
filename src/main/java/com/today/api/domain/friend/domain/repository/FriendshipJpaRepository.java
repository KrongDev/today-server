package com.today.api.domain.friend.domain.repository;

import com.today.api.domain.friend.domain.model.vo.FriendshipStatus;
import com.today.api.domain.friend.infrastructure.entity.FriendshipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendshipJpaRepository extends JpaRepository<FriendshipEntity, Long> {

    @Query("SELECT f FROM FriendshipEntity f WHERE (f.requesterId = :userId OR f.receiverId = :userId) and f.status =:status ")
    List<FriendshipEntity> findAllByUserIdAndStatus(@Param("userId") Long userId, @Param("status") FriendshipStatus status);

    @Query("SELECT f FROM FriendshipEntity f WHERE " +
            "(f.requesterId = :userId AND f.receiverId = :friendId) OR " +
            "(f.requesterId = :friendId AND f.receiverId = :userId)")
    Optional<FriendshipEntity> findByUserIdAndFriendId(@Param("userId") Long userId, @Param("friendId") Long friendId);
}
