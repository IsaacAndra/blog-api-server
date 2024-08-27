package com.isaacandrade.blog.domain.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.isaacandrade.blog.domain.user.UserDTO;

import java.time.LocalDateTime;
import java.util.Date;


@JsonPropertyOrder({"id", "title", "content", "createdAt", "isActive"})
public record PostDTO(Long id, String title, String content, @JsonFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime createdAt, Boolean isActive) {
}