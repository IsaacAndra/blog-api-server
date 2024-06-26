package com.isaacandrade.blog.controller;

import com.isaacandrade.blog.domain.post.CreatePostDTO;
import com.isaacandrade.blog.domain.post.EditPostDTO;
import com.isaacandrade.blog.domain.post.PostDTO;
import com.isaacandrade.blog.service.PostService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    PostService postService;

    @GetMapping
    public ResponseEntity<List<PostDTO>> findAllPosts(){
        List<PostDTO> posts = postService.findAll();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> findPostById(@PathVariable Long id){
        PostDTO postsById = postService.findById(id);
        return ResponseEntity.ok(postsById);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<PostDTO> createPost(@RequestBody CreatePostDTO data){
        PostDTO creatingPost = postService.createPost(data);
        return new ResponseEntity<>(creatingPost, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long id, @RequestBody EditPostDTO data){
        PostDTO editedPost = postService.updatePost(id, data);
        return ResponseEntity.ok(editedPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePost(@PathVariable Long id){
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}