package com.isaacandrade.blog.exception.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProjectNotFoundMessage {

    private int status;
    private String message;
}
