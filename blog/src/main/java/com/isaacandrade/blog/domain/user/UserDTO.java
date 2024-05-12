package com.isaacandrade.blog.domain.user;

public record UserDTO(
        Long id,
        String userName,
        String email,
        String passWord
) {
}
