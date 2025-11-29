package com.today.api.domain.friend.repository;

import com.today.api.domain.friend.model.Friendship;

import java.util.List;
import java.util.Optional;

public interface FriendshipDomainRepository {
    Friendship save(Friendship friendship);

    Optional<Friendship> findById(Long id);

    Optional<Friendship> findByUserIdAndFriendId(Long userId, Long friendId);

    List<Friendship> findAllByUserId(Long userId);

    void delete(Friendship friendship);
}
