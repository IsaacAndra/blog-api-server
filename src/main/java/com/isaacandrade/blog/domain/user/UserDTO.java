package com.isaacandrade.blog.domain.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "userName", "email"})
public record UserDTO(

        Long id,

        @JsonProperty("email")
        String email,

        @JsonProperty("username")
        String userName


) {
}
