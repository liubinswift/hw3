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
   *  ģ��ʵ��������ȡ������10/10����
   *  ����ȡ�����ݣ�1����ʾ1������ʾ�������ȡ��
   *  ���û����ȡ�����ݣ���1����ȡ��
   */
  public void run() {
    for (int runi = 1; runi <= 60; runi++) {
      try {
	HashMap[] getData = MonCacheAccessor.getCacheData(cacheSign, getKey);
	System.out.println(cacheSign + "��ȡ" + runi + "�κ󣬴�" + getKey.intValue() +
			   "ֵ�����ȡ�����");
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
   * ������ʾ��
   * @param getData HashMap[]
   */
  private void displayData(HashMap[] getData, int startPos) {
    if (getData != null) {
      System.out.println(cacheSign + "����������ȡ������" + getData.length + ",��������: ");
      for (int i = 0; i < getData.length; i++) {
	System.out.println(cacheSign + "����" + i + "λ�����ݣ�" +
			   getData[i].get("frequency") + "   ");
	if (i % 11 == 10) {
	  System.out.println(" ");
	}
	//����1����ʾ1�Ρ�
	try {
	  Thread.sleep(1000);
	}
	catch (InterruptedException ex) {
	}
      }
    }
    else {
      System.out.println(cacheSign + "������û����ȡ�������ݣ� ");
      //û������Ҳ��1�����ȥ��ȡ��
      try {
	Thread.sleep(1000);
      }
      catch (InterruptedException ex) {
      }
    }
  }

}
