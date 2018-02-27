package com.viewscenes.app;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.viewscenes.dao.DaoFactory;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.util.business.SimplePing;


/**
 * 站点状态检查线程
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
   * 由此线程检查的站点ID, IP对应关系
   */
  private Map checkSite;

  private boolean bRun = true;

  /**
   * 两次检查间隔时间
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
	String id = (String) i.next(); // 获得ID
	String ip_online = (String) checkSite.get(id); // 获得IP和站点现在库中存储的在线情况。
	String ip=ip_online.split("#")[0];
	String online=ip_online.split("#")[1];
/**
 * 注意：这里一定要加入判断条件，不然的话太频繁修改数据库。
 */
	if (SimplePing.ping(ip)) { // 联通
	  if(online.equals("0"))
		saveStatus(id, 1);
	}
	else {
	if(online.equals("1"))
	    saveStatus(id, 0);
	}

        System.out.println("检查站点：" + id + Thread.currentThread().getName());
      }

      try {
	Thread.currentThread().sleep(SLEEP_TIME);
      }
      catch (InterruptedException ex) {
	ex.printStackTrace();
      }

      System.out.println("检查链路 " + Thread.currentThread().getName());
    }
  }

  /**
   * 保存在线状态
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