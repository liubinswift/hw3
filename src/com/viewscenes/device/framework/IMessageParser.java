package com.viewscenes.device.framework;



import com.viewscenes.device.util.*;



/**

 * ������Ϣ������

 * @version 1.0

 */

public interface IMessageParser {

  /**

   * ��IMessage�������Ϊ�ڲ�����

   * @param msg ��������IMessage����

   * @preconditions msg != null

   * @see IMessage

   */

  void parseMessage(IMessage msg);



  /**

   * ���ظ�parser֧�ֵ���Ϣ����

   * @return ��parser֧�ֵ���Ϣ����

   */

  InnerMsgType getType();

}
