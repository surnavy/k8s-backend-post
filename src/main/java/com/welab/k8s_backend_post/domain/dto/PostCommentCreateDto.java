package com.welab.k8s_backend_post.domain.dto;

import com.welab.k8s_backend_post.domain.PostComment;
import com.welab.k8s_backend_post.web.context.GatewayRequestHeaderUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCommentCreateDto {
    @NotNull(message = "포스트 ID를 입력하세요.")
    private Long postId;
    @NotBlank(message = "댓글을 입력하세요.")
    private String comment;

    public PostComment toEntity() {
        PostComment postComment = new PostComment();
        postComment.setUserId(GatewayRequestHeaderUtils.getUserIdOrThrowException());
        postComment.setComment(this.comment);
        return postComment;
    }
}
