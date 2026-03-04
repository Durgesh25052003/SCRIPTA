package com.projectBloggingBackend.Scripta.dtos;

public class FollowRequestDTO {
    private Long followingId;

    public FollowRequestDTO(){};

    public FollowRequestDTO(Long followerId){
        this.followingId=followerId;
    }


    public Long getFollowingId() {
        return followingId;
    }
}
