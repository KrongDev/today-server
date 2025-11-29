package com.today.api.domain.friend.domain.service;

import com.today.api.domain.friend.domain.model.Friendship;
import com.today.api.domain.friend.domain.model.vo.FriendshipStatus;
import com.today.api.domain.friend.domain.repository.FriendshipDomainRepository;
import com.today.api.global.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendService {

    private final FriendshipDomainRepository friendshipDomainRepository;

    @Transactional
    public Friendship addFriend(Long userId, Long friendId) {
        // Create domain model
        Friendship friendship = Friendship.createRequest(userId, friendId);
        friendship.accept(); // Directly accept for simple add friend

        // Save
        return friendshipDomainRepository.save(friendship);
    }

    public boolean existFriend (Long userId, Long friendId) {
        return friendshipDomainRepository.findByUserIdAndFriendId(userId, friendId).isPresent();
    }

    public List<Friendship> getActiveFriends(Long userId) {
        return friendshipDomainRepository.findAllByUserIdAndStatus(userId, FriendshipStatus.ACCEPTED);
    }

    @Transactional
    public void removeFriend(Long userId, Long friendId) {
        Friendship friendship = friendshipDomainRepository.findByUserIdAndFriendId(userId, friendId)
                .orElseThrow(() -> new ResourceNotFoundException("Friendship not found"));
        friendshipDomainRepository.delete(friendship);
    }

    @Transactional
    public void sendFriendRequest(Long userId, Long friendId) {
        Friendship friendship = Friendship.createRequest(userId, friendId);
        friendshipDomainRepository.save(friendship);
    }

    @Transactional
    public void respondToFriendRequest(Long userId, Long requestId, FriendshipStatus status) {
        // Load domain model
        Friendship friendship = friendshipDomainRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Friend request not found"));

        if (!friendship.getReceiverId().equals(userId)) {
            throw new IllegalArgumentException("Not authorized to respond to this request");
        }

        if (!friendship.isPending()) {
            throw new IllegalArgumentException("Request is not pending");
        }

        if (FriendshipStatus.ACCEPTED == status) {
            // Delegate to domain model
            friendship.accept();
            friendshipDomainRepository.save(friendship);
            return;
        }

        if (FriendshipStatus.BLOCKED == status) {
            friendshipDomainRepository.delete(friendship);
            return;
        }
    }
}
