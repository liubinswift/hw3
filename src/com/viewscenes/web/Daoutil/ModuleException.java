package com.viewscenes.web.Daoutil;

import com.viewscenes.pub.exception.AppException;

public class ModuleException extends AppException{
  public final static int TYPE_COMMON = 0;
  public final static int TYPE_ERROR = 1;
  int type = TYPE_ERROR;

  public ModuleException() {
   super();
 }

 public ModuleException(String message) {
    super(message);
  }

  public ModuleException(String message, int type) {
     super(message);
     this.type = type;
   }

  public ModuleException(String message, Throwable cause) {
     super(message,cause);
  }

  public ModuleException(String message, Throwable cause, int type) {
     super(message+"<br>\r\n  Cause by:"+cause.toString(),cause);
     this.type = type;
  }

  public ModuleException(String message, String method, Throwable cause) {
    super(message+"<br>\r\n Method="+method+" <br>\r\n Cause by:"+cause.getMessage(),cause);
  }

  public int getType(){
    return type;
  }

  public String getHtmlMessage(){
    String message = "<pre>"+getMessage()+"\n";
    if (this.getCause()!=null)
        message+=this.getCause().getMessage();
    message += "</pre>";
    return message;
  }
}
