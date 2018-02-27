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
   *  ģ��ʵ��������ӣ�ÿ5�����5*10���������ݡ�����10/10����
   */
  public void run() {
    for (int runi = 1; runi <= 12; runi++) {
      //ÿ��������ݹ���
      HashMap[] testData = new HashMap[50];
      for (int datai = 0; datai < testData.length; datai++) {
	HashMap rowData = new HashMap();
	rowData.put("frequency", new Integer(dataKey++));
	testData[datai] = rowData;
      }
      //�������
      try {
	MonCacheAccessor.addCacheData(cacheSign, testData,"frequency", Integer.class);
	HashMap[] getData = MonCacheAccessor.getCacheData(cacheSign, new Integer(0));
	System.out.println(cacheSign + "���" + runi + "�κ�Ļ�������" + getData.length);
      }
      catch (Exception ex1) {
	ex1.printStackTrace();
      }
      //˯��5��
      try {
	Thread.currentThread().sleep(5000);
      }
      catch (InterruptedException ex) {
      }

    }
  }
}
