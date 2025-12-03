package com.today.api.domain.tag.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    private Long id;
    private String name;
    private String color;
    private Long userId;

    public static Tag of(String name, String color, Long userId) {
        return Tag.builder()
                .name(name)
                .color(color)
                .userId(userId)
                .build();

    }

    public void update(String name, String color) {
        this.name = name;
        this.color = color;
    }
}
