package com.isaacandrade.blog.domain.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthenticationDTO(

        @JsonProperty("username")
        String userName,
        @JsonProperty("senha")
        String passWord
) {
}
