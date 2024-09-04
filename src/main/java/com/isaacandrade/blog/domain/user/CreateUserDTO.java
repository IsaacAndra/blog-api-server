package com.isaacandrade.blog.domain.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;

public record CreateUserDTO(
        @JsonProperty("username")
        String userName,
        @JsonProperty("email")
        @Email
        String email,
        @JsonProperty("senha")
        String passWord,

        UserRole role
) {
}
