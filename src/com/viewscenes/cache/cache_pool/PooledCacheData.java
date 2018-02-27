package com.viewscenes.cache.cache_pool;

import java.io.*;

/**
 *
 * <p>Title: 缓存详细信息对象 </p>
 * <p>Description: 用于封装实时数据及其索引数组，并提供相应的维护接口。</p>
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

  private Object cacheData = null; //数据缓存
  private long lastModTime = 0; //空闲记数

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
