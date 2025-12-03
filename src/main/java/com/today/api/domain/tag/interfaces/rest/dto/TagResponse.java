package com.today.api.domain.tag.interfaces.rest.dto;

import com.today.api.domain.tag.domain.model.Tag;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TagResponse {
    private Long id;
    private String name;
    private String color;

    public static TagResponse from(Tag tag) {
        return TagResponse.builder()
                .id(tag.getId())
                .name(tag.getName())
                .color(tag.getColor())
                .build();
    }
}
