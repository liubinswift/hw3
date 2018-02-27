package com.viewscenes.device.radio;

import com.viewscenes.device.framework.BaseMessageResponse;




public class MsgDefaultRes extends BaseMessageResponse
{
  private static MsgDefaultRes inst = new MsgDefaultRes();

  public static MsgDefaultRes instance() { return inst;
  }
}
