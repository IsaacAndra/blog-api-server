package com.isaacandrade.blog.service;

import com.isaacandrade.blog.domain.post.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;


    public List<PostDTO> findAll(){
        List<Post> posts = postRepository.findByActiveTrue();
        return posts.stream().map(this::mapToPostDTO).collect(Collectors.toList());
    }

    public PostDTO findById(Long id){
        Post posts = postRepository.findById(id).orElseThrow();
        return mapToPostDTO(posts);
    }

    public PostDTO createPost(createPostDTO data){
        Post post = new Post(data);
        Post savedPost = postRepository.save(post);

        return mapToPostDTO(savedPost);
    }

    public PostDTO updatePost(Long id, EditPostDTO data){
        Post post = postRepository.findById(id).orElseThrow();
        post.updatePost(data);
        postRepository.save(post);

        return mapToPostDTO(post);
    }

    public void deletePost(Long id){
        Post post = postRepository.findById(id).orElseThrow();
        post.delete();
    }

    private PostDTO mapToPostDTO(Post post) {
        return new PostDTO(post.getId(), post.getTitle(), post.getContent(), post.getCreatedAt(), post.getIsActive());
    }
}
