package com.isaacandrade.blog.exception;

public class ConstraintViolationException extends RuntimeException {
    public ConstraintViolationException(){super("There's some field you nedd to fill");}
    public ConstraintViolationException(String message){super(message);}
}
