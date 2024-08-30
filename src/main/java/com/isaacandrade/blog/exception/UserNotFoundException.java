package com.isaacandrade.blog.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(){super("User Was Not Found!");}
    public UserNotFoundException(String message){super(message);}
}
