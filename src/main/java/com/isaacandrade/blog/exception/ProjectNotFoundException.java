package com.isaacandrade.blog.exception;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(){super("Project Was Not Found!");}
    public ProjectNotFoundException(String message){super(message);}
}
