package com.devederno.cursomc.resources.exception;

import com.devederno.cursomc.services.exeptions.DataIntegrityException;
import com.devederno.cursomc.services.exeptions.ObjectNotFoundException;
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
      "Não encontrado",
      e.getClass().getName(),
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
      "Não encontrado",
      e.getClass().getName(),
      request.getRequestURI()
    );

    return ResponseEntity.status(status).body(err);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<StandardError> dataIntegrity(MethodArgumentNotValidException e, HttpServletRequest request) {

    HttpStatus status = HttpStatus.BAD_REQUEST;
    ValidationError err = new ValidationError(
      System.currentTimeMillis(),
      status.value(),
      "Erro de validação",
      e.getClass().getName(),
      request.getRequestURI()
    );

    for (FieldError x : e.getBindingResult().getFieldErrors()) {
      err.addError(x.getField(), x.getDefaultMessage());
    }

    return ResponseEntity.status(status).body(err);
  }
}
