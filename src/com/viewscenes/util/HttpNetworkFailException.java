package com.viewscenes.util;

public class HttpNetworkFailException extends Exception {

  public HttpNetworkFailException() {
  }

  public HttpNetworkFailException(String message) {
    super(message);
  }

  public HttpNetworkFailException(String message, Throwable cause) {
    super(message, cause);
  }

  public HttpNetworkFailException(Throwable cause) {
    super(cause);
  }
}
