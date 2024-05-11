package com.isaacandrade.blog.domain.post;

import java.util.Date;

public record createPostDTO(
        String title,
        String content,
        Date createdAt,
        Boolean isActive
) {
}
