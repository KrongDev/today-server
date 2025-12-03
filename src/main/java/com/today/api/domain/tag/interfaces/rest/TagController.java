package com.today.api.domain.tag.interfaces.rest;

import com.today.api.domain.tag.application.TagFacade;
import com.today.api.domain.tag.interfaces.rest.dto.TagRequest;
import com.today.api.domain.tag.interfaces.rest.dto.TagResponse;
import com.today.api.global.security.oauth2.user.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagFacade tagFacade;

    @PostMapping
    public ResponseEntity<TagResponse> createTag(
            @AuthenticationPrincipal UserPrincipal userDetails,
            @RequestBody TagRequest request) {
        return ResponseEntity.ok(tagFacade.createTag(userDetails.getId(), request));
    }

    @GetMapping
    public ResponseEntity<List<TagResponse>> getTags(
            @AuthenticationPrincipal UserPrincipal userDetails) {
        return ResponseEntity.ok(tagFacade.getTags(userDetails.getId()));
    }

    @PutMapping("/{tagId}")
    public ResponseEntity<TagResponse> updateTag(
            @AuthenticationPrincipal UserPrincipal userDetails,
            @PathVariable Long tagId,
            @RequestBody TagRequest request) {
        return ResponseEntity.ok(tagFacade.updateTag(userDetails.getId(), tagId, request));
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity<Void> deleteTag(
            @AuthenticationPrincipal UserPrincipal userDetails,
            @PathVariable Long tagId) {
        tagFacade.deleteTag(userDetails.getId(), tagId);
        return ResponseEntity.ok().build();
    }
}
