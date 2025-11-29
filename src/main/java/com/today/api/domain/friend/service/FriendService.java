package com.today.api.domain.friend.service;

import com.today.api.domain.friend.dto.*;
import com.today.api.domain.friend.model.Friendship;
import com.today.api.domain.friend.repository.FriendshipDomainRepository;
import com.today.api.domain.user.entity.UserEntity;
import com.today.api.domain.user.repository.UserJpaRepository;
import com.today.api.global.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendService {

    private final FriendshipDomainRepository friendshipDomainRepository;
    private final UserJpaRepository userRepository;

    @Transactional
    public FriendResponse addFriend(Long userId, AddFriendRequest request) {
        // Verify both users exist
        UserEntity requester = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        UserEntity receiver = userRepository.findById(request.getFriendId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.getFriendId()));

        // Check if friendship already exists
        if (friendshipDomainRepository.findByUserIdAndFriendId(userId, request.getFriendId()).isPresent()) {
            throw new IllegalArgumentException("Friendship already exists");
        }

        // Create domain model
        Friendship friendship = Friendship.createRequest(userId, request.getFriendId());
        friendship.accept(); // Directly accept for simple add friend

        // Save
        friendship = friendshipDomainRepository.save(friendship);

        return toFriendResponse(friendship, userId, receiver.getNickname());
    }

    public List<FriendResponse> getFriends(Long userId) {
        return friendshipDomainRepository.findAllByUserId(userId).stream()
                .filter(Friendship::isAccepted)
                .map(friendship -> {
                    Long friendId = friendship.getOtherUserId(userId);
                    UserEntity friend = userRepository.findById(friendId)
                            .orElseThrow(() -> new ResourceNotFoundException("User", "id", friendId));
                    return toFriendResponse(friendship, userId, friend.getNickname());
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void removeFriend(Long userId, Long friendId) {
        Friendship friendship = friendshipDomainRepository.findByUserIdAndFriendId(userId, friendId)
                .orElseThrow(() -> new ResourceNotFoundException("Friendship not found"));
        friendshipDomainRepository.delete(friendship);
    }

    @Transactional
    public void sendFriendRequest(Long userId, SendFriendRequest request) {
        UserEntity requester = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        UserEntity receiver = userRepository.findByEmail(request.getTargetEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", request.getTargetEmail()));

        if (userId.equals(receiver.getId())) {
            throw new IllegalArgumentException("Cannot send friend request to yourself");
        }

        if (friendshipDomainRepository.findByUserIdAndFriendId(userId, receiver.getId()).isPresent()) {
            throw new IllegalArgumentException("Friendship or request already exists");
        }

        // Create domain model
        Friendship friendship = Friendship.createRequest(userId, receiver.getId());

        // Save
        friendshipDomainRepository.save(friendship);
    }

    @Transactional
    public void respondToFriendRequest(Long userId, Long requestId, String status) {
        // Load domain model
        Friendship friendship = friendshipDomainRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Friend request not found"));

        if (!friendship.getReceiverId().equals(userId)) {
            throw new IllegalArgumentException("Not authorized to respond to this request");
        }

        if (!friendship.isPending()) {
            throw new IllegalArgumentException("Request is not pending");
        }

        if ("ACCEPTED".equalsIgnoreCase(status)) {
            // Delegate to domain model
            friendship.accept();
            friendshipDomainRepository.save(friendship);
        } else if ("DENIED".equalsIgnoreCase(status)) {
            friendshipDomainRepository.delete(friendship);
        } else {
            throw new IllegalArgumentException("Invalid status");
        }
    }

    private FriendResponse toFriendResponse(Friendship friendship, Long currentUserId, String friendNickname) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        Long friendId = friendship.getOtherUserId(currentUserId);

        return new FriendResponse(
                friendship.getId(),
                friendId,
                friendNickname,
                friendship.getCreatedAt().format(formatter));
    }
}
