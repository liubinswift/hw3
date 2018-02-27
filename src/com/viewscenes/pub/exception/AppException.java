package com.viewscenes.pub.exception;

public class AppException extends Exception {
  private Throwable cause = null;

  public AppException() {
    super();
  }

  public AppException(String message) {
    super(message);
  }

  public AppException (String message, Throwable cause){
    super(message+"\n"+cause.getMessage());
    this.cause = cause;
  }

  public Throwable getCause(){
    return cause;
  }

  public void printStackTrace(){
    super.printStackTrace();
    if (cause!=null){
      cause.printStackTrace();
    }
  }

  public void printStackTrace(java.io.PrintStream ps){
    super.printStackTrace(ps);
    if (cause!=null){
      cause.printStackTrace(ps);
    }
  }

  public void printStackTrace(java.io.PrintWriter pw){
    super.printStackTrace(pw);
    if (cause!=null){
      cause.printStackTrace(pw);
    }
  }
}
