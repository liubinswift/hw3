package com.viewscenes.util;

public class HttpFileNotFoundException extends Exception {

  public HttpFileNotFoundException() {
  }

  public HttpFileNotFoundException(String message) {
    super(message);
  }

  public HttpFileNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public HttpFileNotFoundException(Throwable cause) {
    super(cause);
  }
}
