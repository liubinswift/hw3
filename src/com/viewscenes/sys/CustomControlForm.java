package com.viewscenes.sys;

import javax.servlet.http.*;
//import uml.*;


public class CustomControlForm {
  public static int AFTER_ADD = 0;
  public static int AFTER_MODIFY = 1;
  public static int AFTER_DEL = 2;

  private String browseData = "";
  private int errFlag = 0;
  private String errMsg = "";
  private int action = 0;

  public int getAction(){
    return action;
  }

  public void setAction(int action){
    this.action = action;
  }

  public String getBrowseData(){
    return browseData;
  }

  public void setBrowseData(String aData){
    this.browseData = aData;
  }

  public int getErrFlag(){
    return errFlag;
  }

  public void setErrFlag(int aFlag){
    this.errFlag = aFlag;
  }

  public String getErrMsg(){
    return errMsg;
  }

  public void setErrMsg(String aMsg){
    this.errMsg = aMsg;
  }

  public void saveInRequest(HttpServletRequest request, String formName){
    request.setAttribute(formName, this);
  }

  public CustomControlForm() {
  }


}
