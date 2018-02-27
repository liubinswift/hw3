package com.viewscenes.device.framework;



import com.viewscenes.device.util.*;



/**

 * <pre>

 * ������Ϣ������

 * ʵ�ָýӿڵ������IMessage����ʽ���ڲ����ݸ�֪�ͻ�

 * ��ͬʱ��֪֧�ֵ���Ϣ�汾

 * </pre>

 * @pattern Builder

 * @version 1.0

 */

public interface IMessageBuilder {

  /**

   * ���ڲ����ݹ���ΪIMessage����

   * @return �����ڲ����ݵ�IMessage����

   * @see MessageElement

   */

  IMessage buildMessage();
  /**

   * ���ظ�builder֧�ֵ���Ϣ����

   * @return ��builder֧�ֵ���Ϣ����

   */
  InnerMsgType getType();

}
