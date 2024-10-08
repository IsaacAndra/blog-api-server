package com.isaacandrade.blog.domain.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Email;

@JsonPropertyOrder({"id", "userName", "email"})
public record UserDTO(

        Long id,

        @JsonProperty("email")
        @Email
        String email,

        @JsonProperty("username")
        String userName,
        @JsonProperty("role")
        UserRole role


) {
}
