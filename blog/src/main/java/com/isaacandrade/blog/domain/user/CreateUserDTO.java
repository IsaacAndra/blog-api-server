package com.isaacandrade.blog.domain.user;

public record CreateUserDTO(
        String userName,
        String email,
        String passWord
) {
}
