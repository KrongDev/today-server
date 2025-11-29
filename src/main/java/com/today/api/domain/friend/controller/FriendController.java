package com.today.api.domain.friend.controller;

import com.today.api.domain.friend.dto.*;
import com.today.api.domain.friend.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @PostMapping
    public ResponseEntity<FriendResponse> addFriend(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody AddFriendRequest request) {
        Long userId = Long.parseLong(userDetails.getUsername());
        return ResponseEntity.ok(friendService.addFriend(userId, request));
    }

    @GetMapping
    public ResponseEntity<List<FriendResponse>> getFriends(
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = Long.parseLong(userDetails.getUsername());
        return ResponseEntity.ok(friendService.getFriends(userId));
    }

    @DeleteMapping("/{friendId}")
    public ResponseEntity<Void> removeFriend(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long friendId) {
        Long userId = Long.parseLong(userDetails.getUsername());
        friendService.removeFriend(userId, friendId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/requests")
    public ResponseEntity<Void> sendFriendRequest(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody SendFriendRequest request) {
        Long userId = Long.parseLong(userDetails.getUsername());
        friendService.sendFriendRequest(userId, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/requests/{requestId}/respond")
    public ResponseEntity<Void> respondToFriendRequest(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long requestId,
            @RequestParam String status) {
        Long userId = Long.parseLong(userDetails.getUsername());
        friendService.respondToFriendRequest(userId, requestId, status);
        return ResponseEntity.ok().build();
    }
}
