package com.isaacandrade.blog.domain.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "email", "userName", "passWord"})
public record UserDTO(

        Long id,
        @JsonProperty("username")
        String userName,
        @JsonProperty("email")
        String email,
        @JsonProperty("password")
        String passWord

) {
}
