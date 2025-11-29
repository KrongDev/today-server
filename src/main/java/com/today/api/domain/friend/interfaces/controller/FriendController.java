package com.today.api.domain.friend.interfaces.controller;

import com.today.api.domain.friend.application.service.FriendShipFacade;
import com.today.api.domain.friend.domain.model.vo.FriendshipStatus;
import com.today.api.domain.friend.interfaces.dto.AddFriendRequest;
import com.today.api.domain.friend.interfaces.dto.FriendResponse;
import com.today.api.domain.friend.interfaces.dto.SendFriendRequest;
import com.today.api.global.security.oauth2.user.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FriendShipFacade friendShipFacade;

    @PostMapping
    public ResponseEntity<FriendResponse> addFriend(
            @AuthenticationPrincipal UserPrincipal userDetails,
            @RequestBody AddFriendRequest request) {
        return ResponseEntity.ok(friendShipFacade.addFriend(userDetails.getId(), request));
    }

    @GetMapping
    public ResponseEntity<List<FriendResponse>> getFriends(
            @AuthenticationPrincipal UserPrincipal userDetails) {
        return ResponseEntity.ok(friendShipFacade.getFriends(userDetails.getId()));
    }

    @DeleteMapping("/{friendId}")
    public ResponseEntity<Void> removeFriend(
            @AuthenticationPrincipal UserPrincipal userDetails,
            @PathVariable Long friendId) {
        friendShipFacade.removeFriend(userDetails.getId(), friendId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/requests")
    public ResponseEntity<Void> sendFriendRequest(
            @AuthenticationPrincipal UserPrincipal userDetails,
            @RequestBody SendFriendRequest request) {
        friendShipFacade.sendFriendRequest(userDetails.getId(), request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/requests/{requestId}/respond")
    public ResponseEntity<Void> respondToFriendRequest(
            @AuthenticationPrincipal UserPrincipal userDetails,
            @PathVariable Long requestId,
            @RequestParam FriendshipStatus status) {
        friendShipFacade.respondToFriendRequest(userDetails.getId(), requestId, status);
        return ResponseEntity.ok().build();
    }
}
