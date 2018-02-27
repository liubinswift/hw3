package com.viewscenes.cache.cache_pool;

import java.util.*;

/**
 *
 * <p>Title: 缓存池对象</p>
 * <p>Description: 用于维护缓存集，并提供相应的访问与修改方法。
 * 注意，此类的方法不考虑并发，要有业务访问器来并发控制。
 * 经测试，用缓存后，时间已经相当过剩，
 * 所以在修改数据内容时，也进行了一定的并发控制，结果对客观需求没有任何影响。
 * </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: novel_tongfang</p>
 * @author lixuefeng
 * @version 1.0
 */
public class DataCachePool {
  public DataCachePool() {
  }

  private static HashMap CACHE_MAP = new HashMap(); //缓存集

  private static final long MAX_ALLOW_TIME = 15 * 60 * 1000; //15分钟。
  private static final long SLEEP_TIME = 20 * 1000; //20秒。

  private static long LAST_CHECK_TIME = System.currentTimeMillis();

  private static Object SYNC_OBJ = new Object(); //并发控制

  /**
   * 数据缓存池提取的对外接口。
   * 根据缓存标志提取相应的缓存对象。具体的解析由访问器来决定。
   * @param cacheSign Object  缓存标志
   * @throws Exception
   * @return Object  （缓存中的数据对象）。
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
   * 缓存数据的修改接口，
   * @param cacheSign Object 缓存标志。
   * @param cacheData Object 新的缓存数据。
   * @throws Exception
   */
  public static void setCacheData(Object cacheSign, Object cacheData) throws
      Exception {
    //构造缓存对象。
    synchronized (SYNC_OBJ) {
      PooledCacheData cacher = (PooledCacheData) CACHE_MAP.get(cacheSign);
      //新对象并发控制。
      if (cacher == null) {
	cacher = new PooledCacheData(cacheSign);
      }
      //变内容
      cacher.setCacheData(cacheData);
      //记数器归0
      cacher.setLastModTime(System.currentTimeMillis());
      CACHE_MAP.put(cacheSign, cacher);

      /**
       * 检查缓存.如果现在时间-上次检查时间超过 睡眠时间 就检查所有对象。
       */
      long newCheckTime = System.currentTimeMillis();
      if (newCheckTime - LAST_CHECK_TIME > SLEEP_TIME) {
	checkCacheMap();
	LAST_CHECK_TIME = newCheckTime;
      }
    }
  }

  /**
   * 缓存数据的对外内存释放接口
   * @param cacheSign String 缓存标志。
   * @param cacheData Object 新的缓存数据。
   * @throws Exception
   */
  public static void freeCacheData(Object cacheSign) {
    //释放缓存数据。
    synchronized (SYNC_OBJ) {
      CACHE_MAP.remove(cacheSign);
    }
  }

  /**
   * 循检内存回收程序。
   */
  private static void checkCacheMap() {
    //迭代器。
    Iterator cacherIt = CACHE_MAP.keySet().iterator();
    long newTime = System.currentTimeMillis();
    while (cacherIt.hasNext()) {
      //每个值对。
      Object cacheSign = cacherIt.next();
      PooledCacheData cacheData = (PooledCacheData) CACHE_MAP.get(cacheSign);
      if (cacheData == null) {
	continue;
      }
      //最大时间／每次时间即为可允许最大空闲次数。
      if (newTime - cacheData.getLastModTime() > MAX_ALLOW_TIME) {
	//释放缓存。
	freeCacheData(cacheSign);
	System.out.println("空闲" + MAX_ALLOW_TIME / (60 * 1000) + "分钟后释放" +
			   cacheSign + "缓存内容！");
      }
    }
  }
}
