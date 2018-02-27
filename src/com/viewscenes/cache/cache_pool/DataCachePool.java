package com.viewscenes.cache.cache_pool;

import java.util.*;

/**
 *
 * <p>Title: ����ض���</p>
 * <p>Description: ����ά�����漯�����ṩ��Ӧ�ķ������޸ķ�����
 * ע�⣬����ķ��������ǲ�����Ҫ��ҵ����������������ơ�
 * �����ԣ��û����ʱ���Ѿ��൱��ʣ��
 * �������޸���������ʱ��Ҳ������һ���Ĳ������ƣ�����Կ͹�����û���κ�Ӱ�졣
 * </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: novel_tongfang</p>
 * @author lixuefeng
 * @version 1.0
 */
public class DataCachePool {
  public DataCachePool() {
  }

  private static HashMap CACHE_MAP = new HashMap(); //���漯

  private static final long MAX_ALLOW_TIME = 15 * 60 * 1000; //15���ӡ�
  private static final long SLEEP_TIME = 20 * 1000; //20�롣

  private static long LAST_CHECK_TIME = System.currentTimeMillis();

  private static Object SYNC_OBJ = new Object(); //��������

  /**
   * ���ݻ������ȡ�Ķ���ӿڡ�
   * ���ݻ����־��ȡ��Ӧ�Ļ�����󡣾���Ľ����ɷ�������������
   * @param cacheSign Object  �����־
   * @throws Exception
   * @return Object  �������е����ݶ��󣩡�
   */
  public static Object getCacheData(Object cacheSign) throws Exception {
    synchronized (SYNC_OBJ) {
      PooledCacheData cacher = (PooledCacheData) CACHE_MAP.get(cacheSign);
      if (cacher == null) {
	return null;
      }
      return cacher.getCacheData();
    }
  }

  /**
   * �������ݵ��޸Ľӿڣ�
   * @param cacheSign Object �����־��
   * @param cacheData Object �µĻ������ݡ�
   * @throws Exception
   */
  public static void setCacheData(Object cacheSign, Object cacheData) throws
      Exception {
    //���컺�����
    synchronized (SYNC_OBJ) {
      PooledCacheData cacher = (PooledCacheData) CACHE_MAP.get(cacheSign);
      //�¶��󲢷����ơ�
      if (cacher == null) {
	cacher = new PooledCacheData(cacheSign);
      }
      //������
      cacher.setCacheData(cacheData);
      //��������0
      cacher.setLastModTime(System.currentTimeMillis());
      CACHE_MAP.put(cacheSign, cacher);

      /**
       * ��黺��.�������ʱ��-�ϴμ��ʱ�䳬�� ˯��ʱ�� �ͼ�����ж���
       */
      long newCheckTime = System.currentTimeMillis();
      if (newCheckTime - LAST_CHECK_TIME > SLEEP_TIME) {
	checkCacheMap();
	LAST_CHECK_TIME = newCheckTime;
      }
    }
  }

  /**
   * �������ݵĶ����ڴ��ͷŽӿ�
   * @param cacheSign String �����־��
   * @param cacheData Object �µĻ������ݡ�
   * @throws Exception
   */
  public static void freeCacheData(Object cacheSign) {
    //�ͷŻ������ݡ�
    synchronized (SYNC_OBJ) {
      CACHE_MAP.remove(cacheSign);
    }
  }

  /**
   * ѭ���ڴ���ճ���
   */
  private static void checkCacheMap() {
    //��������
    Iterator cacherIt = CACHE_MAP.keySet().iterator();
    long newTime = System.currentTimeMillis();
    while (cacherIt.hasNext()) {
      //ÿ��ֵ�ԡ�
      Object cacheSign = cacherIt.next();
      PooledCacheData cacheData = (PooledCacheData) CACHE_MAP.get(cacheSign);
      if (cacheData == null) {
	continue;
      }
      //���ʱ�䣯ÿ��ʱ�伴Ϊ�����������д�����
      if (newTime - cacheData.getLastModTime() > MAX_ALLOW_TIME) {
	//�ͷŻ��档
	freeCacheData(cacheSign);
	System.out.println("����" + MAX_ALLOW_TIME / (60 * 1000) + "���Ӻ��ͷ�" +
			   cacheSign + "�������ݣ�");
      }
    }
  }
}
