package com.today.api.domain.friend.repository;

import com.today.api.domain.friend.entity.FriendshipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendshipJpaRepository extends JpaRepository<FriendshipEntity, Long> {

    @Query("SELECT f FROM FriendshipEntity f WHERE f.requester.id = :userId OR f.receiver.id = :userId")
    List<FriendshipEntity> findAllByUserId(@Param("userId") Long userId);

    @Query("SELECT f FROM FriendshipEntity f WHERE " +
            "(f.requester.id = :userId AND f.receiver.id = :friendId) OR " +
            "(f.requester.id = :friendId AND f.receiver.id = :userId)")
    Optional<FriendshipEntity> findByUserIdAndFriendId(@Param("userId") Long userId, @Param("friendId") Long friendId);
}
