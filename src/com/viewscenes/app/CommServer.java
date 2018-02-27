package com.viewscenes.app;

import com.viewscenes.dao.DaoFactory;
import com.viewscenes.dao.database.DbComponent;
import com.viewscenes.pub.GDSet;


import java.util.*;

/**
 * 
 * @author:刘斌
 * @version:1.0 
 * 类说明:用来检测站点链接
 * @Jul 20, 2011 11:29:21 AM
 */

public class CommServer {
  private static final int CHECK_NUMBER = 10; // 每个线程负责检查的站点个数

  private static Vector checkThread = new Vector();
  private static boolean bStarted = false;

  public CommServer() {
  }

  /**
   * 后台程序，不断检查站点链接状态
   * @throws Exception
   */
  public synchronized void startCheck() throws Exception {

    if (bStarted) {
      return; // 已经启动
    }

    // 初始化
    init();

    // 启动线程
    for (int i = 0; i < checkThread.size(); i++) {
      CheckStatusThread thread = (CheckStatusThread) checkThread.get(i);
      System.out.println(thread.getName()+"启动了！");
      thread.start();
    }

    bStarted = true;
  }

  /**
   * 停止线程
   * @throws Exception
   */
  public void stopCheck() throws Exception {

    if (!bStarted) {
      return; // 已经停止
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
   * 初始化线程数据
   * @throws Exception
   */
  private void init() throws Exception {

    Map map = getSite(); // 获得所有站点
    Set idSet = map.keySet();

    int siteNumber = map.size(); // 站点数目
    int threadNumber = siteNumber / CHECK_NUMBER + 1; // 计算线程个数

    HashMap[] threadSite = new HashMap[threadNumber]; // 建立线程
    for (int i = 0; i < threadNumber; i++) {
      threadSite[i] = new HashMap();

    }
    Iterator it = idSet.iterator();
    int i = 0;
    while (it.hasNext()) {
      int threadIndex = i / CHECK_NUMBER;
      String id = (String) it.next();
      String ip = (String) map.get(id);
      threadSite[threadIndex].put(id, ip); // 建立线程数据
      i++;
    }

    for (i = 0; i < threadNumber; i++) {
      CheckStatusThread checkthread = new CheckStatusThread(threadSite[i]);
      checkThread.add(checkthread);
    }
  }

  /**
   * 获得所有站点
   * 这里需要注意的是站点检测出在线和不在线只有和数据库中存储的不一样的时候才修改库。
   * 不然的话太频繁修改数据库。
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
