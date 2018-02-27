package com.viewscenes.device.framework;



import com.viewscenes.device.util.*;



/**

 * <pre>

 * 构造消息的助手

 * 实现该接口的类可以IMessage的形式将内部数据告知客户

 * 并同时告知支持的消息版本

 * </pre>

 * @pattern Builder

 * @version 1.0

 */

public interface IMessageBuilder {

  /**

   * 将内部数据构造为IMessage对象

   * @return 包含内部数据的IMessage对象

   * @see MessageElement

   */

  IMessage buildMessage();
  /**

   * 返回该builder支持的消息类型

   * @return 该builder支持的消息类型

   */
  InnerMsgType getType();

}
