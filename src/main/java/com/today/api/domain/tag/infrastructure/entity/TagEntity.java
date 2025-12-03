package com.today.api.domain.tag.infrastructure.entity;

import com.today.api.domain.tag.domain.model.Tag;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.BeanUtils;

@Entity
@Setter(AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tags")
public class TagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String color; // Hex color code e.g., "#FF5733"
    @Column(nullable = false)
    private Long userId;

    public TagEntity(Tag tag) {
        BeanUtils.copyProperties(tag, this);
    }

    public Tag toDomain() {
        Tag tag = new Tag();
        BeanUtils.copyProperties(this, tag);
        return tag;
    }
}
