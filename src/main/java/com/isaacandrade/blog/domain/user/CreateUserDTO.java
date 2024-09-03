package com.isaacandrade.blog.domain.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateUserDTO(
        @JsonProperty("username")
        String userName,
        @JsonProperty("email")
        String email,
        @JsonProperty("senha")
        String passWord
) {
}
