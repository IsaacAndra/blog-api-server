package com.isaacandrade.blog.service;

import com.isaacandrade.blog.domain.post.*;
import com.isaacandrade.blog.domain.user.User;
import com.isaacandrade.blog.domain.user.UserDTO;
import com.isaacandrade.blog.exception.ConstraintViolationException;
import com.isaacandrade.blog.exception.PostNotFoundException;
import com.isaacandrade.blog.infra.ApplicationExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationExceptionHandler exceptionHandler;


    public List<PostDTO> findAllActives(){
        List<Post> posts = postRepository.findByIsActiveTrue();
        if (posts.isEmpty()){
            throw new PostNotFoundException();
        }
        return posts.stream().map(this::mapToPostDTO).collect(Collectors.toList());
    }

    public AuthorWithPostsDTO findPostsWithAuthorId(Long authorId){
        UserDTO author = userService.findUserById(authorId);
        List<Post> posts = postRepository.findPostsByAuthorId(authorId);

        if (author == null ) {
            throw new PostNotFoundException();
        }

        List<PostDTO> postDTOs = posts.stream().filter(Post::getIsActive).map(this::mapToPostDTO).collect(Collectors.toList());

        return new AuthorWithPostsDTO(author, postDTOs);
    }

    public PostDTO findById(Long id){
        Post posts = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Post With Id " + id + " Was Not Found"));
        return mapToPostDTO(posts);
    }

    public PostDTO createPost(CreatePostDTO data, Long authorId){
        Post post = new Post();
        UserDTO author = userService.findUserById(authorId);
        post.setTitle(data.title());
        post.setContent(data.content());
        post.setCreatedAt(LocalDateTime.now());
        post.setIsActive(data.isActive() != null ? data.isActive() : true);
        post.setAuthor(mapToUserEntity(author));

        if (data.title() == null || data.content() == null){
            throw new ConstraintViolationException("title and content cannot be null!");
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

    private User mapToUserEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.id());
        user.setEmail(userDTO.email());
        user.setUserName(userDTO.userName());
        return user;
    }

    private PostDTO mapToPostDTO(Post post) {
        UserDTO authorDTO = new UserDTO(post.getAuthor().getId(), post.getAuthor().getEmail(), post.getAuthor().getUserName());
        return new PostDTO(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getIsActive()
        );
    }
}
