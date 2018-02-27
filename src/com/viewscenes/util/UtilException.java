package com.viewscenes.util;

import com.viewscenes.pub.exception.AppException;

public class UtilException extends AppException {
  public UtilException() {
    super();
  }

  public UtilException(String message) {
     super(message);
   }
   public UtilException(String message, Throwable cause) {
      super(message,cause);
    }

}
