package com.viewscenes.cache.mon_cache;

import java.util.*;

//import junit.framework.*;

public class TestMonCache
   // extends TestCase
   {

  private static final int clientTreadCounts = 1;

  public TestMonCache(String fName) {
    //super(fName);
  }

  /**
   * ��ӳ���
   * @throws Exception
   */
  public void testAddData() throws Exception {
    //����20������̡߳�
    for (int i = 1; i <= clientTreadCounts; i++) {
      new TestMonCacheAddThread("testData" + i).start();
    }
  }

  /**
   * ��ȡ����
   * @throws Exception
   */
  public void testGetData() throws Exception {
    //����20����ȡ�̡߳�
    for (int i = 1; i <= clientTreadCounts; i++) {
      new TestMonCacheGetThread("testData" + i).start();
    }
  }

  /**
   * ���Գ��򼯡�
   * @return Test
   */
//  public static Test suite() {
//    TestSuite suite = new TestSuite();
//    suite.addTest(new TestMonCache("testAddData"));
//    suite.addTest(new TestMonCache("testGetData"));
//    return suite;
//  }

//  public static void main(String[] args) {
//    junit.textui.TestRunner.run(suite());
////    junit.textui.TestRunner.run(TestMonCache.class);
////    junit.awtui.TestRunner.run(TestMonCache.class);
//  }
}
