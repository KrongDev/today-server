package com.today.api.domain.friend.controller;

import com.today.api.domain.friend.dto.AddFriendRequest;
import com.today.api.domain.friend.dto.FriendResponse;
import com.today.api.domain.friend.dto.SendFriendRequest;
import com.today.api.domain.friend.service.FriendService;
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

    private final FriendService friendService;

    @PostMapping
    public ResponseEntity<FriendResponse> addFriend(
            @AuthenticationPrincipal UserPrincipal userDetails,
            @RequestBody AddFriendRequest request) {
        return ResponseEntity.ok(friendService.addFriend(userDetails.getId(), request));
    }

    @GetMapping
    public ResponseEntity<List<FriendResponse>> getFriends(
            @AuthenticationPrincipal UserPrincipal userDetails) {
        return ResponseEntity.ok(friendService.getFriends(userDetails.getId()));
    }

    @DeleteMapping("/{friendId}")
    public ResponseEntity<Void> removeFriend(
            @AuthenticationPrincipal UserPrincipal userDetails,
            @PathVariable Long friendId) {
        friendService.removeFriend(userDetails.getId(), friendId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/requests")
    public ResponseEntity<Void> sendFriendRequest(
            @AuthenticationPrincipal UserPrincipal userDetails,
            @RequestBody SendFriendRequest request) {
        friendService.sendFriendRequest(userDetails.getId(), request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/requests/{requestId}/respond")
    public ResponseEntity<Void> respondToFriendRequest(
            @AuthenticationPrincipal UserPrincipal userDetails,
            @PathVariable Long requestId,
            @RequestParam String status) {
        friendService.respondToFriendRequest(userDetails.getId(), requestId, status);
        return ResponseEntity.ok().build();
    }
}
