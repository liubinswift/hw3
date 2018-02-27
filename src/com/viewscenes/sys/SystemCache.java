package com.viewscenes.sys;

import java.util.HashMap;

import com.viewscenes.bean.pub.HeadOnlineStatusBean;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class SystemCache {

  public static String objectqueuepath = null;//队列序列化路径
  /**
   * 存放站点是否在线状态
   */
  public static HashMap<String,HeadOnlineStatusBean> onLineStatusMap = new HashMap<String,HeadOnlineStatusBean>(); 
}
