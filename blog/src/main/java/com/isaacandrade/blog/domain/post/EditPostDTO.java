package com.isaacandrade.blog.domain.post;

public record EditPostDTO(
        String title,
        String content,
        Boolean isActive
) {
}
