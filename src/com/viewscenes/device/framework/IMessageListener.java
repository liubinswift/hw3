package com.viewscenes.device.framework;



/**

 * <pre>

 * ��Ϣ�����ӿڣ�������Ϣ�첽֪ͨ

 * ������JMS��MessageListener��֮���Բ�������Ϊ������JMS

 * ��ʵ�����ڲ���ʹ��JMS

 * </pre>

 * @version 1.0

 */

public interface IMessageListener {

  /**

   * <pre>

   * ��Ϣ����ʱ������ʵ�ִ˽ӿڵĶ���Ĵ˷���

   * �÷������밲ȫ���أ�������ʵ�����׳��κοɲ�׽���쳣

   * �÷������龡�췵��

   * </pre>

   * @param msg IMessage������body���ֵ�Ԫ������������ȫΪСд

   */

  void onMessage(IMessage msg);

}
