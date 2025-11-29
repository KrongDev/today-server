package com.today.api.domain.friend.interfaces.dto;

import com.today.api.domain.friend.domain.model.Friendship;
import com.today.api.domain.friend.domain.model.vo.FriendshipStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static com.today.api.global.GlobalConstant.dateFormatter;

@Getter
@Builder
@AllArgsConstructor
public class FriendResponse {
    private Long friendshipId;
    private Long requesterId;
    private Long receiverId;
    private FriendshipStatus status;
    private String createdAt;

    public static FriendResponse from(Friendship friendship) {
        return FriendResponse.builder()
                .friendshipId(friendship.getId())
                .requesterId(friendship.getRequesterId())
                .receiverId(friendship.getReceiverId())
                .status(friendship.getStatus())
                .createdAt(friendship.getCreatedAt().format(dateFormatter))
                .build();
    }
}
