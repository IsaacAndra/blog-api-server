package com.isaacandrade.blog.exception.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PostNotFoundMessage {
    private int status;
    private String message;
}
