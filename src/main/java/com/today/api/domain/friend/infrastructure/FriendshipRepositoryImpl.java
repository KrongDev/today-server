package com.today.api.domain.friend.infrastructure;

import com.today.api.domain.friend.domain.model.Friendship;
import com.today.api.domain.friend.domain.model.vo.FriendshipStatus;
import com.today.api.domain.friend.domain.repository.FriendshipDomainRepository;
import com.today.api.domain.friend.domain.repository.FriendshipJpaRepository;
import com.today.api.domain.friend.infrastructure.entity.FriendshipEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class FriendshipRepositoryImpl implements FriendshipDomainRepository {
    private final FriendshipJpaRepository friendshipJpaRepository;

    @Override
    public Friendship save(Friendship friendship) {
        FriendshipEntity entity = new FriendshipEntity(friendship);
        return friendshipJpaRepository.save(entity).toDomain();
    }

    @Override
    public Optional<Friendship> findById(Long id) {
        return friendshipJpaRepository.findById(id)
                .map(FriendshipEntity::toDomain);
    }

    @Override
    public Optional<Friendship> findByUserIdAndFriendId(Long userId, Long friendId) {
        return friendshipJpaRepository.findByUserIdAndFriendId(userId, friendId)
                .map(FriendshipEntity::toDomain);
    }

    @Override
    public List<Friendship> findAllByUserIdAndStatus(Long userId, FriendshipStatus status) {
        return friendshipJpaRepository.findAllByUserIdAndStatus(userId, status).stream()
                .map(FriendshipEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Friendship friendship) {
        if (friendship.getId() != null) {
            friendshipJpaRepository.deleteById(friendship.getId());
        }
    }
}
