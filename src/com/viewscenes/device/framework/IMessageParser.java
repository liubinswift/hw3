package com.viewscenes.device.framework;



import com.viewscenes.device.util.*;



/**

 * 解析消息的助手

 * @version 1.0

 */

public interface IMessageParser {

  /**

   * 将IMessage对象解析为内部数据

   * @param msg 待解析的IMessage对象

   * @preconditions msg != null

   * @see IMessage

   */

  void parseMessage(IMessage msg);



  /**

   * 返回该parser支持的消息类型

   * @return 该parser支持的消息类型

   */

  InnerMsgType getType();

}
