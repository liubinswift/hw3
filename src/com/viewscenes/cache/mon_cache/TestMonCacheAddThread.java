package com.viewscenes.cache.mon_cache;

import java.util.*;

public class TestMonCacheAddThread
    extends Thread {

  public TestMonCacheAddThread(String cacheSign) {
    this.cacheSign = cacheSign;
  }

  private String cacheSign = null;
  private int dataKey = 0;

  /**
   *  模拟实际数据添加，每5秒添加5*10条有序数据。持续10/10分钟
   */
  public void run() {
    for (int runi = 1; runi <= 12; runi++) {
      //每次添加数据构造
      HashMap[] testData = new HashMap[50];
      for (int datai = 0; datai < testData.length; datai++) {
	HashMap rowData = new HashMap();
	rowData.put("frequency", new Integer(dataKey++));
	testData[datai] = rowData;
      }
      //添加数据
      try {
	MonCacheAccessor.addCacheData(cacheSign, testData,"frequency", Integer.class);
	HashMap[] getData = MonCacheAccessor.getCacheData(cacheSign, new Integer(0));
	System.out.println(cacheSign + "添加" + runi + "次后的缓存量有" + getData.length);
      }
      catch (Exception ex1) {
	ex1.printStackTrace();
      }
      //睡眠5秒
      try {
	Thread.currentThread().sleep(5000);
      }
      catch (InterruptedException ex) {
      }

    }
  }
}
