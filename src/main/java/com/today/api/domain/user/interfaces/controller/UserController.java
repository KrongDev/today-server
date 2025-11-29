package com.today.api.domain.user.interfaces.controller;

import com.today.api.domain.user.application.service.UserFacade;
import com.today.api.domain.user.interfaces.dto.UpdateProfileRequest;
import com.today.api.domain.user.interfaces.dto.UserProfileResponse;
import com.today.api.domain.user.interfaces.dto.UserResponse;
import com.today.api.global.security.oauth2.user.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserFacade userFacade;

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getMyProfile(
            @AuthenticationPrincipal UserPrincipal userDetails) {
        return ResponseEntity.ok(userFacade.getMyProfile(userDetails.getId()));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileResponse> getUserProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(userFacade.getUserProfile(userId));
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateMyProfile(
            @AuthenticationPrincipal UserPrincipal userDetails,
            @RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok(userFacade.updateMyProfile(userDetails.getId(), request));
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deactivateMyAccount(
            @AuthenticationPrincipal UserPrincipal userDetails) {
        userFacade.deactivateMyAccount(userDetails.getId());
        return ResponseEntity.ok().build();
    }
}
