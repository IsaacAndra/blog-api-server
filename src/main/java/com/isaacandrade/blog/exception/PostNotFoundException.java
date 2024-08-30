package com.isaacandrade.blog.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(){super("Post Was Not Found");}
    public PostNotFoundException(String message){super(message);}
}
