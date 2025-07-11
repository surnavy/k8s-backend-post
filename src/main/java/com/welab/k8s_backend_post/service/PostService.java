package com.welab.k8s_backend_post.service;

import com.welab.k8s_backend_post.common.exception.NotFound;
import com.welab.k8s_backend_post.domain.Post;
import com.welab.k8s_backend_post.domain.PostComment;
import com.welab.k8s_backend_post.domain.dto.PostCommentCreateDto;
import com.welab.k8s_backend_post.domain.dto.PostCreateDto;
import com.welab.k8s_backend_post.domain.event.PostCommentEvent;
import com.welab.k8s_backend_post.domain.repository.PostCommentRepository;
import com.welab.k8s_backend_post.domain.repository.PostRepository;
import com.welab.k8s_backend_post.event.producer.KafkaMessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostCommentRepository postCommentRepository;
    private final KafkaMessageProducer kafkaMessageProducer;

    @Transactional
    public void createPost(PostCreateDto createDto) {
        Post post = createDto.toEntity();
        postRepository.save(post);
    }

    @Transactional
    public void addPostComment(PostCommentCreateDto createDto) {
        Post post = postRepository.findById(createDto.getPostId())
                .orElseThrow(() -> new NotFound("포스팅 글을 찾을 수 없습니다."));
        PostComment postComment = createDto.toEntity();
        postCommentRepository.save(postComment);
        post.addComment(postComment);
        kafkaMessageProducer.send(
                PostCommentEvent.Topic,
                PostCommentEvent.fromEntity("Create", postComment)
        );
    }
}
