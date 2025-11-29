package com.today.api.domain.friend.application.service;

import com.today.api.domain.friend.domain.model.Friendship;
import com.today.api.domain.friend.domain.model.vo.FriendshipStatus;
import com.today.api.domain.friend.domain.service.FriendService;
import com.today.api.domain.friend.interfaces.dto.AddFriendRequest;
import com.today.api.domain.friend.interfaces.dto.FriendResponse;
import com.today.api.domain.friend.interfaces.dto.SendFriendRequest;
import com.today.api.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FriendShipFacade {

    private final FriendService friendService;
    private final UserService userService;

    @Transactional
    public FriendResponse addFriend(Long userId, AddFriendRequest request) {
        // Verify both users exist
        userService.getUserProfile(userId);
        userService.getUserProfile(request.friendId());
        // Check if friendship already exists
        if (friendService.existFriend(userId, request.friendId())) {
            throw new IllegalArgumentException("Friendship already exists");
        }
        // Save
        Friendship friendship = friendService.addFriend(userId, request.friendId());

        return FriendResponse.from(friendship);
    }

    @Transactional(readOnly = true)
    public List<FriendResponse> getFriends(Long userId) {
        // FIXME Projection Object Query
        return friendService.getActiveFriends(userId).stream()
                .map(FriendResponse::from)
                .collect(Collectors.toList());
    }

    public void removeFriend(Long userId, Long friendId) {
        friendService.removeFriend(userId, friendId);
    }

    @Transactional
    public void sendFriendRequest(Long userId, SendFriendRequest request) {
        userService.getUserProfile(userId);
        userService.getUserProfile(request.friendId());

        if (userId.equals(request.friendId())) {
            throw new IllegalArgumentException("Cannot send friend request to yourself");
        }

        if (friendService.existFriend(userId, request.friendId())) {
            throw new IllegalArgumentException("Friendship already exists");
        }
        friendService.sendFriendRequest(userId, request.friendId());
    }

    @Transactional
    public void respondToFriendRequest(Long userId, Long requestId, FriendshipStatus status) {
        friendService.respondToFriendRequest(userId, requestId, status);
    }
}
