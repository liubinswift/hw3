package com.viewscenes.app;

import com.viewscenes.dao.DaoFactory;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.pub.GDSet;


import java.util.*;

/**
 * 
 * @author:����
 * @version:1.0 
 * ��˵��:�������վ������
 * @Jul 20, 2011 11:29:21 AM
 */

public class CommServer {
  private static final int CHECK_NUMBER = 10; // ÿ���̸߳������վ�����

  private static Vector checkThread = new Vector();
  private static boolean bStarted = false;

  public CommServer() {
  }

  /**
   * ��̨���򣬲��ϼ��վ������״̬
   * @throws Exception
   */
  public synchronized void startCheck() throws Exception {

    if (bStarted) {
      return; // �Ѿ�����
    }

    // ��ʼ��
    init();

    // �����߳�
    for (int i = 0; i < checkThread.size(); i++) {
      CheckStatusThread thread = (CheckStatusThread) checkThread.get(i);
      System.out.println(thread.getName()+"�����ˣ�");
      thread.start();
    }

    bStarted = true;
  }

  /**
   * ֹͣ�߳�
   * @throws Exception
   */
  public void stopCheck() throws Exception {

    if (!bStarted) {
      return; // �Ѿ�ֹͣ
    }

    for (int i = 0; i < checkThread.size(); i++) {
      CheckStatusThread thread = (CheckStatusThread) checkThread.get(i);
     
      thread.Stop();
    }

    bStarted = false;
  }

  public void keeyCheck() throws Exception {

  }

  /**
   * ��ʼ���߳�����
   * @throws Exception
   */
  private void init() throws Exception {

    Map map = getSite(); // �������վ��
    Set idSet = map.keySet();

    int siteNumber = map.size(); // վ����Ŀ
    int threadNumber = siteNumber / CHECK_NUMBER + 1; // �����̸߳���

    HashMap[] threadSite = new HashMap[threadNumber]; // �����߳�
    for (int i = 0; i < threadNumber; i++) {
      threadSite[i] = new HashMap();

    }
    Iterator it = idSet.iterator();
    int i = 0;
    while (it.hasNext()) {
      int threadIndex = i / CHECK_NUMBER;
      String id = (String) it.next();
      String ip = (String) map.get(id);
      threadSite[threadIndex].put(id, ip); // �����߳�����
      i++;
    }

    for (i = 0; i < threadNumber; i++) {
      CheckStatusThread checkthread = new CheckStatusThread(threadSite[i]);
      checkThread.add(checkthread);
    }
  }

  /**
   * �������վ��
   * ������Ҫע�����վ��������ߺͲ�����ֻ�к����ݿ��д洢�Ĳ�һ����ʱ����޸Ŀ⡣
   * ��Ȼ�Ļ�̫Ƶ���޸����ݿ⡣
   * @return
   * @throws Exception
   */
  private Map getSite() throws Exception {
    HashMap map = new HashMap();

    DbComponent db = (DbComponent) DaoFactory.create(DaoFactory.DB_OBJECT);
    String sql = "select head_id, ip ,is_online from res_headend_tab where is_delete=0 order by head_id";
    try {
      GDSet gd = db.Query(sql);

      for (int i = 0; i < gd.getRowCount(); i++) {
	String id = gd.getString(i, "head_id");
	String ip = gd.getString(i, "ip")+"#"+gd.getString(i, "is_online");
	map.put(id, ip);
      }
    }
    catch (Exception ex) {
      throw new Exception("", ex);
    }

    return map;
  }

  public static void main(String[] args) {
    CommServer server = new CommServer();
    try {
      server.startCheck();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
