package com.today.api.domain.tag.interfaces.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TagRequest {
    private String name;
    private String color;
}
