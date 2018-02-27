package com.viewscenes.cache.mon_cache;

import java.util.*;

public class TestMonCacheGetThread
    extends Thread {

  public TestMonCacheGetThread(String cacheSign) {
    this.cacheSign = cacheSign;
  }

  private String cacheSign = null;
  private Integer getKey = new Integer(0);

  /**
   *  模拟实际数据提取。持续10/10分钟
   *  所提取的数据，1秒显示1条，显示完后再提取。
   *  如果没有提取到数据，隔1秒再取。
   */
  public void run() {
    for (int runi = 1; runi <= 60; runi++) {
      try {
	HashMap[] getData = MonCacheAccessor.getCacheData(cacheSign, getKey);
	System.out.println(cacheSign + "提取" + runi + "次后，从" + getKey.intValue() +
			   "值后的提取结果：");
	displayData(getData, getKey.intValue());
	if (getData != null) {
	  getKey = (Integer) (getData[getData.length - 1].get("frequency"));
	}
      }
      catch (Exception ex1) {
	ex1.printStackTrace();
      }
    }
  }

  /**
   * 数据显示。
   * @param getData HashMap[]
   */
  private void displayData(HashMap[] getData, int startPos) {
    if (getData != null) {
      System.out.println(cacheSign + "缓存中现提取到数据" + getData.length + ",具体如下: ");
      for (int i = 0; i < getData.length; i++) {
	System.out.println(cacheSign + "缓存" + i + "位的数据：" +
			   getData[i].get("frequency") + "   ");
	if (i % 11 == 10) {
	  System.out.println(" ");
	}
	//第条1秒显示1次。
	try {
	  Thread.sleep(1000);
	}
	catch (InterruptedException ex) {
	}
      }
    }
    else {
      System.out.println(cacheSign + "缓存中没有提取到新数据！ ");
      //没有数据也用1秒后再去提取。
      try {
	Thread.sleep(1000);
      }
      catch (InterruptedException ex) {
      }
    }
  }

}
