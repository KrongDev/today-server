package com.today.api.domain.friend.domain.repository;

import com.today.api.domain.friend.domain.model.Friendship;
import com.today.api.domain.friend.domain.model.vo.FriendshipStatus;

import java.util.List;
import java.util.Optional;

public interface FriendshipDomainRepository {
    Friendship save(Friendship friendship);

    Optional<Friendship> findById(Long id);

    Optional<Friendship> findByUserIdAndFriendId(Long userId, Long friendId);

    List<Friendship> findAllByUserIdAndStatus(Long userId, FriendshipStatus status);

    void delete(Friendship friendship);
}
