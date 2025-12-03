package com.today.api.domain.tag.infrastructure;

import com.today.api.domain.tag.domain.model.Tag;
import com.today.api.domain.tag.infrastructure.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagJpaRepository extends JpaRepository<TagEntity, Long> {
    List<Tag> findAllByUserId(Long userId);
}
