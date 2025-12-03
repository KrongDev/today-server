package com.today.api.domain.tag.domain.service;

import com.today.api.domain.tag.domain.model.Tag;
import com.today.api.domain.tag.domain.repository.TagRepository;
import com.today.api.domain.tag.interfaces.rest.dto.TagRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {

    private final TagRepository tagRepository;

    @Transactional
    public Tag createTag(Long userId, TagRequest request) {
        return tagRepository.save(Tag.of(request.getName(), request.getColor(), userId));
    }

    public List<Tag> findTags(Long userId) {
        return tagRepository.findAllByUserId(userId);
    }

    public Tag findTag(Long tagId) {
        return tagRepository.findById(tagId)
                .orElseThrow(() -> new IllegalArgumentException("Tag not found"));
    }

    @Transactional
    public Tag updateTag(Long userId, Long tagId, TagRequest request) {
        Tag tag = this.findTag(tagId);

        if (!tag.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Unauthorized");
        }

        tag.update(request.getName(), request.getColor());
        return tagRepository.save(tag);
    }

    @Transactional
    public void deleteTag(Long userId, Long tagId) {
        Tag tag = this.findTag(tagId);
        if (!tag.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Unauthorized");
        }

        tagRepository.remove(tagId);
    }
}
