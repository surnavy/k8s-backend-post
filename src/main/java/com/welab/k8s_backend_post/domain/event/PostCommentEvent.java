package com.welab.k8s_backend_post.domain.event;

import com.welab.k8s_backend_post.domain.PostComment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostCommentEvent {
    public static final String Topic = "postComment";
    private String action;
    private String comment;
    private String userId;
    private LocalDateTime eventTime;

    public static PostCommentEvent fromEntity(String action, PostComment siteUser) {
        PostCommentEvent event = new PostCommentEvent();
        event.action = action;
        event.userId = siteUser.getUserId();
        event.comment = siteUser.getComment();
        event.eventTime = LocalDateTime.now();
        return event;
    }
}
