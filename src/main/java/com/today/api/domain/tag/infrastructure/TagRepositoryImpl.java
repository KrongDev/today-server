package com.today.api.domain.tag.infrastructure;

import com.today.api.domain.tag.domain.model.Tag;
import com.today.api.domain.tag.domain.repository.TagRepository;
import com.today.api.domain.tag.infrastructure.entity.TagEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepository {

    private final TagJpaRepository jpaRepository;

    @Override
    public Tag save(Tag tag) {
        return jpaRepository.save(new TagEntity(tag))
                .toDomain();
    }

    @Override
    public List<Tag> findAllByUserId(Long userId) {
        return jpaRepository.findAllByUserId(userId);
    }

    @Override
    public Optional<Tag> findById(Long tagId) {
        return jpaRepository.findById(tagId)
                .map(TagEntity::toDomain);
    }

    @Override
    public void remove(Long tagId) {
        jpaRepository.deleteById(tagId);
    }
}
