package com.viewscenes.util;

public class ConfigFileException extends UtilException {
  public ConfigFileException() {
    super();
  }

  public ConfigFileException(String message) {
     super(message);
   }
   public ConfigFileException(String message, Throwable cause) {
      super(message,cause);
    }

}
