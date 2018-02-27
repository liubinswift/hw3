package com.viewscenes.pub;

import com.viewscenes.pub.exception.AppException;

public class GDSetException extends AppException {
  public GDSetException() {
    super();
  }

  public GDSetException(String message) {
     super(message);
   }
   public GDSetException(String message, Throwable cause) {
      super(message,cause);
    }

}
