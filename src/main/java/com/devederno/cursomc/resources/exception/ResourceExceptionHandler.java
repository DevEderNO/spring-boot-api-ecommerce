package com.devederno.cursomc.resources.exception;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.devederno.cursomc.services.exeptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

  @ExceptionHandler(ObjectNotFoundException.class)
  public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    StandardError err = new StandardError(
      System.currentTimeMillis(),
      status.value(),
      "Not found",
      e.getMessage(),
      request.getRequestURI()
    );

    return ResponseEntity.status(status).body(err);
  }

  @ExceptionHandler(DataIntegrityException.class)
  public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    StandardError err = new StandardError(
      System.currentTimeMillis(),
      status.value(),
      "Data integrity error",
      e.getMessage(),
      request.getRequestURI()
    );

    return ResponseEntity.status(status).body(err);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<StandardError> dataIntegrity(MethodArgumentNotValidException e, HttpServletRequest request) {

    HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
    ValidationError err = new ValidationError(
      System.currentTimeMillis(),
      status.value(),
      "Validation error",
      e.getMessage(),
      request.getRequestURI()
    );

    for (FieldError x : e.getBindingResult().getFieldErrors()) {
      err.addError(x.getField(), x.getDefaultMessage());
    }

    return ResponseEntity.status(status).body(err);
  }

  @ExceptionHandler(AuthorizationException.class)
  public ResponseEntity<StandardError> authorization(AuthorizationException e, HttpServletRequest request) {
    HttpStatus status = HttpStatus.FORBIDDEN;
    StandardError err = new StandardError(
      System.currentTimeMillis(),
      status.value(),
      "Data integrity error",
      e.getMessage(),
      request.getRequestURI()
    );

    return ResponseEntity.status(status).body(err);
  }

  @ExceptionHandler(FileException.class)
  public ResponseEntity<StandardError> authorization(FileException e, HttpServletRequest request) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    StandardError err = new StandardError(
      System.currentTimeMillis(),
      status.value(),
      "File error",
      e.getMessage(),
      request.getRequestURI()
    );

    return ResponseEntity.status(status).body(err);
  }

  @ExceptionHandler(AmazonServiceException.class)
  public ResponseEntity<StandardError> authorization(AmazonServiceException e, HttpServletRequest request) {
    HttpStatus code = HttpStatus.valueOf(e.getErrorCode());
    StandardError err = new StandardError(
      System.currentTimeMillis(),
      code.value(),
      "Amazon service error",
      e.getMessage(),
      request.getRequestURI()
    );

    return ResponseEntity.status(code).body(err);
  }

  @ExceptionHandler(AmazonClientException.class)
  public ResponseEntity<StandardError> authorization(AmazonClientException e, HttpServletRequest request) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    StandardError err = new StandardError(
      System.currentTimeMillis(),
      status.value(),
      "Amazon client error",
      e.getMessage(),
      request.getRequestURI()
    );

    return ResponseEntity.status(status).body(err);
  }

  @ExceptionHandler(AmazonS3Exception.class)
  public ResponseEntity<StandardError> authorization(AmazonS3Exception e, HttpServletRequest request) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    StandardError err = new StandardError(
      System.currentTimeMillis(),
      status.value(),
      "S3 error",
      e.getMessage(),
      request.getRequestURI()
    );

    return ResponseEntity.status(status).body(err);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<StandardError> access(AccessDeniedException e, HttpServletRequest request) {
    HttpStatus status = HttpStatus.FORBIDDEN;
    StandardError err = new StandardError(
      System.currentTimeMillis(),
      status.value(),
      "Forbidden",
      e.getMessage(),
      request.getRequestURI()
    );

    return ResponseEntity.status(status).body(err);
  }
}
