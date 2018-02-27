package com.viewscenes.app;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.viewscenes.dao.DaoFactory;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.util.business.SimplePing;


/**
 * վ��״̬����߳�
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class CheckStatusThread
    extends Thread {
  /**
   * �ɴ��̼߳���վ��ID, IP��Ӧ��ϵ
   */
  private Map checkSite;

  private boolean bRun = true;

  /**
   * ���μ����ʱ��
   */
  private final long SLEEP_TIME = 3000;

  public CheckStatusThread(Map site) {
    checkSite = site;
  }

  public void Stop() {
    bRun = false;
  }

  public void run() {
    while (bRun) {
      Set ipSet = checkSite.keySet();
      Iterator i = ipSet.iterator();
      while (i.hasNext()) {
	String id = (String) i.next(); // ���ID
	String ip_online = (String) checkSite.get(id); // ���IP��վ�����ڿ��д洢�����������
	String ip=ip_online.split("#")[0];
	String online=ip_online.split("#")[1];
/**
 * ע�⣺����һ��Ҫ�����ж���������Ȼ�Ļ�̫Ƶ���޸����ݿ⡣
 */
	if (SimplePing.ping(ip)) { // ��ͨ
	  if(online.equals("0"))
		saveStatus(id, 1);
	}
	else {
	if(online.equals("1"))
	    saveStatus(id, 0);
	}

        System.out.println("���վ�㣺" + id + Thread.currentThread().getName());
      }

      try {
	Thread.currentThread().sleep(SLEEP_TIME);
      }
      catch (InterruptedException ex) {
	ex.printStackTrace();
      }

      System.out.println("�����· " + Thread.currentThread().getName());
    }
  }

  /**
   * ��������״̬
   * @param status
   */
  private void saveStatus(String id, int status) {
    DbComponent db = (DbComponent) DaoFactory.create(DaoFactory.DB_OBJECT);
    String sql = "update res_headend_tab set is_online = " + status +
	" where head_id = " + id;
    try {
      db.exeUpdate(sql);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

  }
}