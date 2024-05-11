package com.isaacandrade.blog.domain.post;

import java.util.Date;

public record CreatePostDTO(
        String title,
        String content,
        Date createdAt,
        Boolean isActive
) {
}
