package com.today.api.domain.friend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequestResponse {
    private Long requestId;
    private Long requesterId;
    private String requesterNickname;
    private String status;
    private String createdAt;
}
