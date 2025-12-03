package com.today.api.domain.tag.domain.repository;

import com.today.api.domain.tag.domain.model.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository {
    Tag save(Tag tag);
    List<Tag> findAllByUserId(Long userId);
    Optional<Tag> findById(Long tagId);
    void remove(Long tagId);
}
