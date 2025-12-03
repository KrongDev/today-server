package com.today.api.domain.tag.application;

import com.today.api.domain.tag.domain.model.Tag;
import com.today.api.domain.tag.domain.service.TagService;
import com.today.api.domain.tag.interfaces.rest.dto.TagRequest;
import com.today.api.domain.tag.interfaces.rest.dto.TagResponse;
import com.today.api.domain.user.domain.model.User;
import com.today.api.domain.user.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagFacade {

    private final TagService tagService;
    private final UserService userService;

    public TagResponse createTag(Long userId, TagRequest request) {
        User user = userService.getUserProfile(userId);
        Tag savedTag = tagService.createTag(user.getId(), request);
        return TagResponse.from(savedTag);
    }

    public List<TagResponse> getTags(Long userId) {
        return tagService.findTags(userId).stream()
                .map(TagResponse::from).collect(Collectors.toList());
    }

    public TagResponse getTag(Long tagId) {
        return TagResponse.from(tagService.findTag(tagId));
    }

    public TagResponse updateTag(Long userId, Long tagId, TagRequest request) {
        Tag tag = tagService.updateTag(userId, tagId, request);
        return TagResponse.from(tag);
    }

    public void deleteTag(Long userId, Long tagId) {
        tagService.deleteTag(userId, tagId);
    }
}
