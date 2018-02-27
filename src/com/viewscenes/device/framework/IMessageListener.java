package com.viewscenes.device.framework;



/**

 * <pre>

 * 消息监听接口，用于消息异步通知

 * 类似于JMS的MessageListener，之所以不用是因为引入了JMS

 * 但实现者内部可使用JMS

 * </pre>

 * @version 1.0

 */

public interface IMessageListener {

  /**

   * <pre>

   * 消息到达时将调用实现此接口的对象的此方法

   * 该方法必须安全返回，不允许实现者抛出任何可捕捉的异常

   * 该方法建议尽快返回

   * </pre>

   * @param msg IMessage对象，其body部分的元素名及属性名全为小写

   */

  void onMessage(IMessage msg);

}
