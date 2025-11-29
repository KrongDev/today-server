package com.today.api.domain.friend.infrastructure;

import com.today.api.domain.friend.entity.FriendshipEntity;
import com.today.api.domain.friend.model.Friendship;
import com.today.api.domain.friend.repository.FriendshipDomainRepository;
import com.today.api.domain.friend.repository.FriendshipJpaRepository;
import com.today.api.domain.user.entity.UserEntity;
import com.today.api.domain.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class FriendshipRepositoryImpl implements FriendshipDomainRepository {

    private final FriendshipJpaRepository friendshipJpaRepository;
    private final UserJpaRepository userJpaRepository;

    @Override
    public Friendship save(Friendship friendship) {
        // Load UserEntities
        UserEntity requester = userJpaRepository.findById(friendship.getRequesterId())
                .orElseThrow(() -> new IllegalArgumentException("Requester not found"));
        UserEntity receiver = userJpaRepository.findById(friendship.getReceiverId())
                .orElseThrow(() -> new IllegalArgumentException("Receiver not found"));

        // Domain -> Entity
        FriendshipEntity entity = new FriendshipEntity(friendship, requester, receiver);

        // Save
        FriendshipEntity savedEntity = friendshipJpaRepository.save(entity);

        // Entity -> Domain
        return savedEntity.toDomain();
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
    public List<Friendship> findAllByUserId(Long userId) {
        return friendshipJpaRepository.findAllByUserId(userId).stream()
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
