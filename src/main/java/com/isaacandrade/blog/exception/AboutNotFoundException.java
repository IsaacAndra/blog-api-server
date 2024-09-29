package com.isaacandrade.blog.exception;

public class AboutNotFoundException extends Throwable {
    public AboutNotFoundException () {super("About Not Found Exception");}
    public AboutNotFoundException (String message) {super(message);}
}
