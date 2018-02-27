package com.viewscenes.device.framework;

import com.viewscenes.device.exception.*;

public class MessageObserver {
  Object msgID;
  BaseMessageCommand cmd;
  BaseMessageResponse res;
  long timeOut;

  public MessageObserver(){

  }

  public MessageObserver(Object msgID, BaseMessageCommand cmd, BaseMessageResponse res) {
    this.msgID = msgID;
    this.cmd = cmd;
    this.res = res;
    this.timeOut = cmd.getTimedout()*1000;
  }

  public void setAttr(Object msgID, BaseMessageCommand cmd, BaseMessageResponse res) {
    this.msgID = msgID;
    this.cmd = cmd;
    this.res = res;
    this.timeOut = cmd.getTimedout()*1000;

  }

  public BaseMessageResponse getResponse(){
    if (res.msg==null){
//      try {
//        wait();
//      }
//      catch (InterruptedException ex) {
//      }
    }
    return res;
  }

  public void setResponse(IMessage msg){
    try {
      res.setMessage(msg);
//      notify();
    }
    catch (DeviceReportException ex) {
//      notifyAll();
    }
  }
}
