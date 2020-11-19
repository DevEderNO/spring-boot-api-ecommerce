package com.devederno.cursomc.services.exeptions;


public class AccessDeniedException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public AccessDeniedException(String msg) {
    super(msg);
  }

  public AccessDeniedException(String msg, Throwable cause) {
    super(msg, cause);
  }

}
