package com.isaacandrade.blog.domain.post;

import java.util.Date;

public record PostDTO(Long id, String title, String content, Date createdAt, Boolean isActive) {
}
