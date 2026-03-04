package com.projectBloggingBackend.Scripta.model;

import jakarta.persistence.*;

import java.lang.reflect.Type;

@Entity
@Table(
        name="follows",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"follower_id", "following_id"})
        }
)
public class Follow {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User follower;

    @ManyToOne
    @JoinColumn(name="following_id")
    private User following;

    public Follow(){};

    public Follow(User followerId,User followingId){
        this.follower=followerId;
        this.following=followingId;
    }

    public void setFollowerId(User followerId) {
        this.follower = followerId;
    }

    public void setFollowingId(User followingId) {
        this.following= followingId;
    }

    public User getFollower() {
        return follower;
    }

    public User getFollowing() {
        return following;
    }
}
