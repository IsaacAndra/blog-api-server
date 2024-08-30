package com.isaacandrade.blog.domain.post;

import com.isaacandrade.blog.domain.user.UserDTO;

import java.util.List;

public record AuthorWithPostsDTO(UserDTO author, List<PostDTO> posts) {
}
