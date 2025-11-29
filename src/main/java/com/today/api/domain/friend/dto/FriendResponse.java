package com.today.api.domain.friend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FriendResponse {
    private Long friendshipId;
    private Long userId;
    private String nickname;
    private String createdAt;
}
