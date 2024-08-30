package com.isaacandrade.blog.domain.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;


@JsonPropertyOrder({"id", "title", "content", "createdAt", "isActive"})
public record PostDTO(Long id, String title, String content, @JsonFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime createdAt, Boolean isActive) {
}