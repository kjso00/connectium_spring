package com.ohgiraffers.crud_back.service;

import com.ohgiraffers.crud_back.model.entity.PostEntity;
import com.ohgiraffers.crud_back.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Value("${ftp.server}")
    private String FTP_SERVER;

    @Value("${ftp.port}")
    private int FTP_PORT;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostEntity> getAllPosts() {
        return postRepository.findAll().stream()
                .map(this::enhancePostWithImageUrl)
                .collect(Collectors.toList());
    }

    public PostEntity createPost(PostEntity postEntity) {
        if (postEntity.getAuthor() == null || postEntity.getAuthor().isEmpty()) {
            throw new IllegalArgumentException("작성자가 비거나 널값이면 안돼유");
        }
        return postRepository.save(postEntity);
    }

    public Optional<PostEntity> getPostById(Long id) {
        return postRepository.findById(id).map(this::enhancePostWithImageUrl);
    }

    private PostEntity enhancePostWithImageUrl(PostEntity post) {
        if (post.getImagePath() != null && !post.getImagePath().isEmpty()) {
            String imageUrl = String.format("/api/images/%s", post.getImagePath());
            post.setImagePath(imageUrl);
        }
        return post;
    }
}