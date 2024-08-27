package com.isaacandrade.blog.domain.post;



public record CreatePostDTO(
        String title,
        String content,
        Boolean isActive
) {
}
