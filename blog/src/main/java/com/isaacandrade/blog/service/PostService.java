package com.isaacandrade.blog.service;

import com.isaacandrade.blog.domain.post.*;
import com.isaacandrade.blog.exception.ConstraintViolationException;
import com.isaacandrade.blog.exception.PostNotFoundException;
import com.isaacandrade.blog.infra.ApplicationExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ApplicationExceptionHandler exceptionHandler;


    public List<PostDTO> findAll(){
        List<Post> posts = postRepository.findByIsActiveTrue();
        if (posts.isEmpty()){
            throw new PostNotFoundException();
        }
        return posts.stream().map(this::mapToPostDTO).collect(Collectors.toList());
    }

    public PostDTO findById(Long id){
        Post posts = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Post With Id " + id + " Was Not Found"));
        return mapToPostDTO(posts);
    }

    public PostDTO createPost(CreatePostDTO data){
        Post post = new Post(data);
        if (data.title() == null || data.content() == null || data.createdAt() == null){
            throw new ConstraintViolationException("title, content and createdAt cannot be null!");
        }
        Post savedPost = postRepository.save(post);

        return mapToPostDTO(savedPost);
    }

    public PostDTO updatePost(Long id, EditPostDTO data){
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Post With Id " + id + " Was Not Found"));
        post.updatePost(data);
        postRepository.save(post);

        return mapToPostDTO(post);
    }

    public void deletePost(Long id){
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Post With Id " + id + " Was Not Found"));
        post.delete();
        postRepository.save(post);
    }

    private PostDTO mapToPostDTO(Post post) {
        return new PostDTO(post.getId(), post.getTitle(), post.getContent(), post.getCreatedAt(), post.getIsActive());
    }
}
