package com.isaacandrade.blog.infra.exceptionhandler;

import com.isaacandrade.blog.exception.*;
import com.isaacandrade.blog.exception.message.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<PostNotFoundMessage> postNotFoundHandler(PostNotFoundException e){
      PostNotFoundMessage threatResponse = new PostNotFoundMessage(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ConstraintViolationMessage> postViolationException(ConstraintViolationException e){
        ConstraintViolationMessage threatResponse = new ConstraintViolationMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<UserNotFoundMessage> userNotFoundHandler(UserNotFoundException e){
        UserNotFoundMessage threatResponse = new UserNotFoundMessage(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ProjectNotFoundMessage> projectNotFoundHandler(ProjectNotFoundException e) {
        ProjectNotFoundMessage threatResponse = new ProjectNotFoundMessage(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(AboutNotFoundException.class)
    public ResponseEntity<AboutNotFoundMessage> projectNotFoundHandler(AboutNotFoundException e) {
        AboutNotFoundMessage threatResponse = new AboutNotFoundMessage(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }
}
