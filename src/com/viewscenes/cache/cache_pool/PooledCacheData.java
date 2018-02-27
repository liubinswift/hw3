package com.viewscenes.cache.cache_pool;

import java.io.*;

/**
 *
 * <p>Title: ������ϸ��Ϣ���� </p>
 * <p>Description: ���ڷ�װʵʱ���ݼ����������飬���ṩ��Ӧ��ά���ӿڡ�</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: novel_tongfang</p>
 * @author lixuefeng
 * @version 1.0
 */
public class PooledCacheData
    implements Serializable {

  public PooledCacheData(Object cacheSign) throws Exception {
    this.cacheSign = cacheSign;
    lastModTime = 0;
  }

  private Object cacheSign = null;

  private Object cacheData = null; //���ݻ���
  private long lastModTime = 0; //���м���

  public void setCacheData(Object cacheData) {
    this.cacheData = cacheData;
  }

  public Object getCacheData() {
    return cacheData;
  }

  public void setCacheSign(Object cacheSign) {
    this.cacheSign = cacheSign;
  }

  public Object getCacheSign() {
    return cacheSign;
  }

  public void setLastModTime(long lastModTime) {
    this.lastModTime = lastModTime;
  }

  public long getLastModTime() {
    return lastModTime;
  }
}
